package com.example.dartscheckout

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dartscheckout.data.CheckoutVariant
import com.example.dartscheckout.data.SettingsStorage
import com.example.dartscheckout.data.checkoutsToJson
import com.example.dartscheckout.data.db.AppDatabase
import com.example.dartscheckout.data.db.CheckoutProgressRepository
import com.example.dartscheckout.data.db.CheckoutRepository
import com.example.dartscheckout.data.jsonToCheckouts
import com.example.dartscheckout.data.readTextFromUri
import com.example.dartscheckout.data.writeTextToUri
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.DarkBg
import com.example.dartscheckout.theme.TileBgDark
import com.example.dartscheckout.ui.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.get(this)
        val repository = CheckoutRepository(db.checkoutDao())
        val progressRepository = CheckoutProgressRepository(db.checkoutProgressDao())
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize(), color = DarkBg) {
                    RootWithSplash(repository, progressRepository)
                }
            }
        }
    }
}

@Composable
fun RootWithSplash(
    repository: CheckoutRepository,
    progressRepository: CheckoutProgressRepository
) {
    val context = LocalContext.current
    var showSplash by remember { mutableStateOf(true) }
    var showWelcome by remember { mutableStateOf(false) }
    val alpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        // 1) Заставка держится 3 секунды
        delay(3000)
        // 2) Плавно растворяется за 1 секунду
        alpha.animateTo(0f, animationSpec = tween(1000))
        // 3) Заставка убрана с экрана
        showSplash = false
        // 4) Небольшая пауза, чтобы экран точно был «чистым»
        delay(300)
        // 5) Только теперь показываем приветствие (и только один раз за всё время)
        if (!SettingsStorage.isWelcomeShown(context)) {
            showWelcome = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DartsApp(
            repository = repository,
            progressRepository = progressRepository,
            showWelcome = showWelcome,
            onWelcomeClose = {
                SettingsStorage.setWelcomeShown(context)
                showWelcome = false
            }
        )

        if (showSplash) {
            SplashContent(alpha = alpha.value)
        }
    }
}

@Composable
fun SplashContent(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { this.alpha = alpha }
            .background(DarkBg),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun DartsApp(
    repository: CheckoutRepository,
    progressRepository: CheckoutProgressRepository,
    showWelcome: Boolean,
    onWelcomeClose: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val checkouts = remember { mutableStateMapOf<Int, List<CheckoutVariant>>() }
    var loaded by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf<String?>(null) }

    var screen by remember { mutableStateOf("main") }
    var selectedRange by remember { mutableStateOf(60..99) }
    var selectedNumber by remember { mutableStateOf<Int?>(null) }
    var editIndex by remember { mutableStateOf(-1) }

    var sortDescending by remember { mutableStateOf(SettingsStorage.isSortDescending(context)) }

    val progressList by progressRepository.allProgress
        .collectAsState(initial = emptyList())
    var progressMode by remember { mutableStateOf(ProgressMode.TRAINING) }

    LaunchedEffect(Unit) {
        repository.seedIfEmpty()
        val all = repository.getAllOnce()
        all.forEach { (num, list) -> checkouts[num] = list }
        loaded = true
    }

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch {
                try {
                    val json = checkoutsToJson(checkouts.toMap())
                    writeTextToUri(context, uri, json)
                    toastMessage = "База экспортирована"
                } catch (e: Exception) {
                    toastMessage = "Ошибка экспорта: ${e.message}"
                }
            }
        }
    }

    val importLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch {
                try {
                    val json = readTextFromUri(context, uri)
                    if (json == null) {
                        toastMessage = "Не удалось прочитать файл"
                        return@launch
                    }
                    val parsed = jsonToCheckouts(json)
                    if (parsed == null) {
                        toastMessage = "Неверный формат файла"
                        return@launch
                    }
                    repository.replaceAll(parsed)
                    checkouts.clear()
                    parsed.forEach { (num, list) -> checkouts[num] = list }
                    toastMessage = "База импортирована"
                } catch (e: Exception) {
                    toastMessage = "Ошибка импорта: ${e.message}"
                }
            }
        }
    }

    if (!loaded) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Загрузка...", color = Color.White)
        }
        return
    }

    fun persist(num: Int) {
        val list = checkouts[num] ?: return
        scope.launch { repository.saveAllForNumber(num, list) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            screen == "main" -> MainMenuScreen(
                onRangeClick = { range -> selectedRange = range; screen = "range" },
                onSettings = { screen = "settings" },
                onHelp = { screen = "help" },
                onCalculator = { screen = "calc" },
                onMyCheckouts = { screen = "my_checkouts" }
            )
            screen == "my_checkouts" -> MyCheckoutsScreen(
                progress = progressList,
                mode = progressMode,
                onModeChange = { progressMode = it },
                onIncrement = { num ->
                    scope.launch {
                        if (progressMode == ProgressMode.TRAINING)
                            progressRepository.incrementTraining(num)
                        else
                            progressRepository.incrementCompetition(num)
                    }
                },
                onDecrement = { num ->
                    scope.launch {
                        if (progressMode == ProgressMode.TRAINING)
                            progressRepository.decrementTraining(num)
                        else
                            progressRepository.decrementCompetition(num)
                    }
                },
                onBack = { screen = "main" }
            )
            screen == "range" -> RangeScreen(
                range = selectedRange,
                descending = sortDescending,
                onNumberClick = { n -> selectedNumber = n; screen = "number" },
                onBack = { screen = "main" }
            )
            screen == "number" -> {
                val num = selectedNumber
                if (num != null) {
                    NumberScreen(
                        number = num,
                        variants = checkouts[num].orEmpty(),
                        onEditVariant = { idx -> editIndex = idx; screen = "edit" },
                        onAddVariant = { editIndex = -1; screen = "edit" },
                        onDelete = { idx ->
                            val list = (checkouts[num] ?: emptyList()).toMutableList()
                            if (idx in list.indices) {
                                list.removeAt(idx)
                                checkouts[num] = list
                                persist(num)
                            }
                        },
                        onMakeMain = { idx ->
                            val list = (checkouts[num] ?: emptyList()).toMutableList()
                            if (idx in list.indices) {
                                val item = list.removeAt(idx)
                                list.add(0, item)
                                checkouts[num] = list
                                persist(num)
                            }
                        },
                        onMoveUp = { idx ->
                            val list = (checkouts[num] ?: emptyList()).toMutableList()
                            if (idx > 0 && idx < list.size) {
                                val tmp = list[idx]
                                list[idx] = list[idx - 1]
                                list[idx - 1] = tmp
                                checkouts[num] = list
                                persist(num)
                            }
                        },
                        onMoveDown = { idx ->
                            val list = (checkouts[num] ?: emptyList()).toMutableList()
                            if (idx >= 0 && idx < list.size - 1) {
                                val tmp = list[idx]
                                list[idx] = list[idx + 1]
                                list[idx + 1] = tmp
                                checkouts[num] = list
                                persist(num)
                            }
                        },
                        onBack = { screen = "range" }
                    )
                }
            }
            screen == "edit" -> {
                val num = selectedNumber
                if (num != null) {
                    val initial = if (editIndex >= 0) checkouts[num]?.getOrNull(editIndex) else null
                    EditVariantScreen(
                        number = num,
                        initial = initial,
                        onSave = { newVariant: CheckoutVariant ->
                            val list = (checkouts[num] ?: emptyList()).toMutableList()
                            if (editIndex == -1) {
                                list.add(newVariant)
                            } else if (editIndex in list.indices) {
                                list[editIndex] = newVariant
                            }
                            checkouts[num] = list
                            persist(num)
                            screen = "number"
                        },
                        onCancel = { screen = "number" }
                    )
                }
            }
            screen == "settings" -> SettingsScreen(
                onBack = { screen = "main" },
                sortDescending = sortDescending,
                onSortDescendingChange = { value ->
                    sortDescending = value
                    SettingsStorage.setSortDescending(context, value)
                },
                onExport = {
                    exportLauncher.launch("darts-checkout-backup.json")
                },
                onImport = {
                    importLauncher.launch(arrayOf("application/json", "text/plain", "*/*"))
                },
                onReset = {
                    scope.launch {
                        repository.resetToDefaults()
                        val all = repository.getAllOnce()
                        checkouts.clear()
                        all.forEach { (num, list) -> checkouts[num] = list }
                        toastMessage = "Сброшено к заводским"
                    }
                }
            )
            screen == "help" -> HelpScreen(onBack = { screen = "main" })
            screen == "calc" -> CalculatorScreen(onBack = { screen = "main" })
        }

        if (showWelcome) {
            WelcomeDialog(onClose = onWelcomeClose)
        }

        toastMessage?.let { msg ->
            LaunchedEffect(msg) {
                delay(2000)
                toastMessage = null
            }
            Box(
                modifier = Modifier.fillMaxSize().padding(bottom = 40.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Surface(
                    color = Color(0xFF37474F),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        msg,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeDialog(onClose: () -> Unit) {
    Dialog(onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(TileBgDark)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    "Darts Checkout",
                    color = Accent,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "Здравствуйте!",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                val scrollState = rememberScrollState()
                val atBottom by remember {
                    derivedStateOf {
                        scrollState.maxValue == 0 ||
                        scrollState.value >= scrollState.maxValue - 10
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 440.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                    ) {
                        WelcomeText()

                        Spacer(Modifier.height(20.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Accent)
                                .clickable { onClose() }
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Понятно",
                                color = Color(0xFF121212),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.height(4.dp))
                    }

                    if (!atBottom) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(Accent.copy(alpha = 0.9f))
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "↓  Прокрутите вниз",
                                color = Color(0xFF121212),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WelcomeText() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            "Это сообщение вы видите в первый и последний раз — больше оно не появится. Пожалуйста, прочитайте его до конца.",
            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
        )
        Text(
            "Это полностью бесплатное приложение — без подписок и рекламы.",
            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
        )
        Text(
            "Что внутри:",
            color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Bold
        )
        Text(
            "• Основные способы закрытия чекаутов 60–170\n" +
            "• Альтернативные пути и варианты при промахе\n" +
            "• Возможность добавлять, редактировать, удалять и переставлять свои закрытия\n" +
            "• Дартс-калькулятор для подсчёта очков",
            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
        )
        Text(
            "В разделе «Помощь» — инструкция по использованию, связь с разработчиком и реквизиты для тех, кто хочет отблагодарить. Даже ваша помощь 10 руб. очень помогут в наших будущих проектах.",
            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
        )
        Text(
            "Спасибо, что вы с нами!",
            color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Bold
        )
    }
}

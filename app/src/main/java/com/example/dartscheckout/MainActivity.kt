package com.example.dartscheckout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.dartscheckout.data.CheckoutVariant
import com.example.dartscheckout.data.db.AppDatabase
import com.example.dartscheckout.data.db.CheckoutRepository
import com.example.dartscheckout.theme.DarkBg
import com.example.dartscheckout.ui.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = AppDatabase.get(this).checkoutDao()
        val repository = CheckoutRepository(dao)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize(), color = DarkBg) {
                    DartsApp(repository)
                }
            }
        }
    }
}

@Composable
fun DartsApp(repository: CheckoutRepository) {
    val scope = rememberCoroutineScope()
    val checkouts = remember { mutableStateMapOf<Int, List<CheckoutVariant>>() }
    var loaded by remember { mutableStateOf(false) }

    var screen by remember { mutableStateOf("main") }
    var selectedRange by remember { mutableStateOf(60..99) }
    var selectedNumber by remember { mutableStateOf<Int?>(null) }
    var editIndex by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        repository.seedIfEmpty()
        val all = repository.getAllOnce()
        all.forEach { (num, list) -> checkouts[num] = list }
        loaded = true
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

    when {
        screen == "main" -> MainMenuScreen(
            onRangeClick = { range -> selectedRange = range; screen = "range" },
            onSettings = { screen = "settings" },
            onCalculator = { screen = "calc" }
        )
        screen == "range" -> RangeScreen(
            range = selectedRange,
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
        screen == "settings" -> SettingsScreen(onBack = { screen = "main" })
        screen == "calc" -> CalculatorScreen(onBack = { screen = "main" })
    }
}

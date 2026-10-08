package com.example.dartscheckout.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dartscheckout.data.db.CheckoutProgressEntity
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.DarkBg
import com.example.dartscheckout.theme.ErrorColor
import com.example.dartscheckout.theme.GoldAccent
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

enum class ProgressMode { TRAINING, COMPETITION }

private val IMPOSSIBLE = setOf(159, 162, 163, 165, 166, 168, 169)
private val ALL_NUMBERS: List<Int> = (2..170).filter { it !in IMPOSSIBLE }

@Composable
fun MyCheckoutsScreen(
    progress: List<CheckoutProgressEntity>,
    mode: ProgressMode,
    onModeChange: (ProgressMode) -> Unit,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
    onBack: () -> Unit
) {
    val progressMap = remember(progress) { progress.associateBy { it.number } }

    var actionFor by remember { mutableStateOf<Int?>(null) }
    var infoFor by remember { mutableStateOf<Int?>(null) }
    var showHelp by remember { mutableStateOf(false) }

    val total = ALL_NUMBERS.size
    val closedCount = ALL_NUMBERS.count { num ->
        val e = progressMap[num]
        val c = if (mode == ProgressMode.TRAINING) e?.trainingCount ?: 0 else e?.competitionCount ?: 0
        c > 0
    }
    val remainPercent = if (total == 0) 0 else ((total - closedCount) * 100 / total)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(TileBgDark)
                    .clickable { onBack() }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Text("← Назад", color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.width(10.dp))

            // Кнопка «?» в кружочке
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Accent)
                    .clickable { showHelp = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "?",
                    color = Color(0xFF121212),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                "Осталось $remainPercent%",
                color = GoldAccent,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(TileBgDark)
                .padding(4.dp)
        ) {
            ModeTab(
                label = "Тренировка",
                selected = mode == ProgressMode.TRAINING,
                modifier = Modifier.weight(1f),
                onClick = { onModeChange(ProgressMode.TRAINING) }
            )
            ModeTab(
                label = "Соревнования",
                selected = mode == ProgressMode.COMPETITION,
                modifier = Modifier.weight(1f),
                onClick = { onModeChange(ProgressMode.COMPETITION) }
            )
        }

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 44.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(ALL_NUMBERS) { num ->
                val e = progressMap[num]
                val count =
                    if (mode == ProgressMode.TRAINING) e?.trainingCount ?: 0
                    else e?.competitionCount ?: 0
                NumberTile(
                    number = num,
                    count = count,
                    onShortClick = { infoFor = num },
                    onLongClick = { actionFor = num }
                )
            }
        }
    }

    // Диалог долгого нажатия
    actionFor?.let { num ->
        val e = progressMap[num]
        val count =
            if (mode == ProgressMode.TRAINING) e?.trainingCount ?: 0
            else e?.competitionCount ?: 0
        Dialog(onDismissRequest = { actionFor = null }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(TileBgDark)
                    .padding(20.dp)
            ) {
                Text(
                    "Число $num",
                    color = Accent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    if (mode == ProgressMode.TRAINING) "Тренировка" else "Соревнования",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "Закрыт $count ${timesWord(count)}",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(20.dp))
                DialogButton(
                    label = "Закрыл  +1",
                    bg = Color(0xFF4CAF50),
                    textColor = Color.White
                ) {
                    onIncrement(num)
                    actionFor = null
                }
                Spacer(Modifier.height(10.dp))
                DialogButton(
                    label = "Ошибся  −1",
                    bg = ErrorColor,
                    textColor = Color.White
                ) {
                    onDecrement(num)
                    actionFor = null
                }
                Spacer(Modifier.height(10.dp))
                DialogButton(
                    label = "Отмена",
                    bg = TileBg,
                    textColor = Color.White
                ) {
                    actionFor = null
                }
            }
        }
    }

    // Инфо-диалог (короткое нажатие)
    infoFor?.let { num ->
        val e = progressMap[num]
        val count =
            if (mode == ProgressMode.TRAINING) e?.trainingCount ?: 0
            else e?.competitionCount ?: 0
        val path =
            if (mode == ProgressMode.TRAINING) e?.trainingLastPath
            else e?.competitionLastPath
        val date =
            if (mode == ProgressMode.TRAINING) e?.trainingLastDate
            else e?.competitionLastDate
        Dialog(onDismissRequest = { infoFor = null }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(TileBgDark)
                    .padding(20.dp)
            ) {
                Text(
                    "Число $num",
                    color = GoldAccent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    if (count == 0) "Ещё не закрывал"
                    else "Закрыт $count ${timesWord(count)}",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                if (!path.isNullOrBlank()) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Путь закрытия:",
                        color = Accent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(path, color = Color.White, fontSize = 14.sp)
                }
                if (date != null) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Последнее: ${formatDate(date)}",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }
                Spacer(Modifier.height(20.dp))
                DialogButton(
                    label = "Закрыть",
                    bg = Accent,
                    textColor = Color(0xFF121212)
                ) {
                    infoFor = null
                }
            }
        }
    }

    // Диалог помощи «?»
    if (showHelp) {
        Dialog(onDismissRequest = { showHelp = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(TileBgDark)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        "Зачем нужен этот раздел?",
                        color = GoldAccent,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(14.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 460.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "Здесь ты отмечаешь, какие чекауты уже закрывал. Это нужно не для статистики ради — а для твоей уверенности.",
                            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
                        )
                        Text(
                            "Когда ты видишь, что закрывал это число 3, 5, 10 раз — мозг перестаёт бояться. Ты уже делал это раньше, значит сможешь и сейчас.",
                            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Как отмечать:",
                            color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Bold
                        )
                        Text(
                            "1. Выбери вкладку — Тренировка или Соревнования.\n" +
                            "2. Найди нужное число в таблице.\n" +
                            "3. Долго удержи палец на числе (около 2 секунд) — откроется меню.\n" +
                            "4. Нажми «Закрыл +1», если закрыл. Или «Ошибся −1», если нажал случайно.",
                            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Цвета подскажут твой прогресс:",
                            color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Bold
                        )
                        Text(
                            "• Серый — пока не закрывал\n" +
                            "• Светло-зелёный — 1–2 раза\n" +
                            "• Зелёный — 3–4 раза\n" +
                            "• Жёлтый — 5–9 раз\n" +
                            "• Красный — 10–49 раз\n" +
                            "• Фиолетовый — 50+ раз (мастер!)",
                            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Цель: закрыть все 162 чекаута. Золотые проценты вверху покажут, сколько осталось.",
                            color = Color.White, fontSize = 14.sp, lineHeight = 20.sp
                        )
                        Text(
                            "Чем больше закрываешь — тем увереннее играешь. Удачи! 🎯",
                            color = GoldAccent, fontSize = 14.sp, fontWeight = FontWeight.Bold, lineHeight = 20.sp
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    DialogButton(
                        label = "Понятно",
                        bg = Accent,
                        textColor = Color(0xFF121212)
                    ) {
                        showHelp = false
                    }
                }
            }
        }
    }
}

@Composable
private fun ModeTab(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(9.dp))
            .background(if (selected) Accent else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            color = if (selected) Color(0xFF121212) else Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NumberTile(
    number: Int,
    count: Int,
    onShortClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val bg = colorForCount(count)
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .combinedClickable(
                onClick = onShortClick,
                onLongClick = onLongClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            number.toString(),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DialogButton(
    label: String,
    bg: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = textColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

private fun colorForCount(count: Int): Color = when {
    count <= 0 -> TileBgDark
    count <= 2 -> Color(0xFF81C784)
    count <= 4 -> Color(0xFF388E3C)
    count <= 9 -> Color(0xFFF9A825)
    count <= 49 -> Color(0xFFC62828)
    else -> Color(0xFF6A1B9A)
}

private fun timesWord(n: Int): String {
    val mod10 = n % 10
    val mod100 = n % 100
    return when {
        mod10 == 1 && mod100 != 11 -> "раз"
        mod10 in 2..4 && mod100 !in 12..14 -> "раза"
        else -> "раз"
    }
}

private fun formatDate(ms: Long): String {
    val sdf = java.text.SimpleDateFormat("dd.MM.yyyy HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date(ms))
}

package com.example.dartscheckout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DarkBg = Color(0xFF121212)
val TileBg = Color(0xFF4A6572)
val TileBgDark = Color(0xFF37474F)
val Accent = Color(0xFF4FC3F7)
val OpColor = Color(0xFF0288D1)

data class Dart(val sector: Int, val multiplier: Int) {
    val score: Int get() = sector * multiplier
    override fun toString(): String = when {
        sector == 25 && multiplier == 2 -> "BULL"
        sector == 25 -> "25"
        multiplier == 1 -> "S$sector"
        multiplier == 2 -> "D$sector"
        else -> "T$sector"
    }
}

fun fmtNumber(v: Double): String =
    if (v == v.toLong().toDouble()) v.toLong().toString() else v.toString()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize(), color = DarkBg) {
                    DartsApp()
                }
            }
        }
    }
}

@Composable
fun DartsApp() {
    var screen by remember { mutableStateOf("main") }
    var selectedRange by remember { mutableStateOf(60..99) }
    var selectedNumber by remember { mutableStateOf<Int?>(null) }

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
        screen == "number" -> PlaceholderScreen(
            title = "Число ${selectedNumber ?: ""}",
            onBack = { screen = "range" }
        )
        screen == "settings" -> PlaceholderScreen(
            title = "Настройки и инструкция",
            onBack = { screen = "main" }
        )
        screen == "calc" -> CalculatorScreen(onBack = { screen = "main" })
    }
}

@Composable
fun MainMenuScreen(
    onRangeClick: (IntRange) -> Unit,
    onSettings: () -> Unit,
    onCalculator: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Darts Checkout",
            fontSize = 28.sp, fontWeight = FontWeight.Bold,
            color = Color.White, modifier = Modifier.padding(bottom = 4.dp)
        )
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            BigButton("60–99", Modifier.weight(1f).fillMaxHeight()) { onRangeClick(60..99) }
            BigButton("100–134", Modifier.weight(1f).fillMaxHeight()) { onRangeClick(100..134) }
        }
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            BigButton("135–170", Modifier.weight(1f).fillMaxHeight()) { onRangeClick(135..170) }
            Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallButton("Калькулятор", Modifier.fillMaxWidth().weight(1f), onCalculator)
                SmallButton("Настройки\nи инструкция", Modifier.fillMaxWidth().weight(1f), onSettings)
            }
        }
    }
}

@Composable
fun RangeScreen(range: IntRange, onNumberClick: (Int) -> Unit, onBack: () -> Unit) {
    val numbers = remember(range) { range.toList() }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text("${range.first}–${range.last}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) { items(numbers) { n -> NumberTile(n) { onNumberClick(n) } } }
    }
}

@Composable
fun NumberTile(number: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier.aspectRatio(1f).clip(RoundedCornerShape(14.dp))
            .background(TileBg).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(number.toString(), fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

@Composable
fun CalculatorScreen(onBack: () -> Unit) {
    var display by remember { mutableStateOf("0") }
    var accumulator by remember { mutableStateOf<Double?>(null) }
    var pendingOp by remember { mutableStateOf<String?>(null) }
    var replaceOnNext by remember { mutableStateOf(true) }

    fun compute() {
        val acc = accumulator ?: return
        val cur = display.toDoubleOrNull() ?: 0.0
        val res = when (pendingOp) {
            "+" -> acc + cur
            "−" -> acc - cur
            "×" -> acc * cur
            "÷" -> if (cur != 0.0) acc / cur else 0.0
            else -> cur
        }
        display = fmtNumber(res)
        accumulator = null
        pendingOp = null
        replaceOnNext = true
    }
    fun onDigit(d: String) {
        if (replaceOnNext) { display = d; replaceOnNext = false }
        else display = if (display == "0") d else display + d
    }
    fun onDot() {
        if (replaceOnNext) { display = "0."; replaceOnNext = false }
        else if (!display.contains(".")) display += "."
    }
    fun onDart(score: Int) {
        val cur = display.toDoubleOrNull() ?: 0.0
        display = fmtNumber(cur + score)
        replaceOnNext = true
    }
    fun onOp(op: String) {
        if (pendingOp != null && !replaceOnNext) compute()
        accumulator = display.toDoubleOrNull() ?: 0.0
        pendingOp = op
        replaceOnNext = true
    }
    fun onEq() { if (pendingOp != null) compute() }
    fun onClear() { display = "0"; accumulator = null; pendingOp = null; replaceOnNext = true }
    fun onBackspace() {
        if (replaceOnNext) display = "0"
        else display = if (display.length > 1) display.dropLast(1) else "0"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 14.dp, vertical = 8.dp)
            ) { Text("← Назад", color = Accent, fontSize = 14.sp) }
            Spacer(Modifier.width(10.dp))
            Text("Калькулятор", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Box(
            modifier = Modifier.fillMaxWidth().height(64.dp)
                .clip(RoundedCornerShape(14.dp)).background(TileBgDark)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(display, color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        }

        Column(
            modifier = Modifier.fillMaxWidth().weight(1.6f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (n in 20 downTo 10) {
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    DartCell(Dart(n, 1), Modifier.weight(1f)) { onDart(it) }
                    DartCell(Dart(n, 2), Modifier.weight(1f)) { onDart(it) }
                    DartCell(Dart(n, 3), Modifier.weight(1f)) { onDart(it) }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("C", Modifier.weight(1f)) { onClear() }
                CalcBtn("⌫", Modifier.weight(1f)) { onBackspace() }
                CalcBtn("%", Modifier.weight(1f)) {
                    display = fmtNumber((display.toDoubleOrNull() ?: 0.0) / 100.0)
                    replaceOnNext = true
                }
                CalcBtn("÷", Modifier.weight(1f), OpColor) { onOp("÷") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("7", Modifier.weight(1f)) { onDigit("7") }
                CalcBtn("8", Modifier.weight(1f)) { onDigit("8") }
                CalcBtn("9", Modifier.weight(1f)) { onDigit("9") }
                CalcBtn("×", Modifier.weight(1f), OpColor) { onOp("×") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("4", Modifier.weight(1f)) { onDigit("4") }
                CalcBtn("5", Modifier.weight(1f)) { onDigit("5") }
                CalcBtn("6", Modifier.weight(1f)) { onDigit("6") }
                CalcBtn("−", Modifier.weight(1f), OpColor) { onOp("−") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("1", Modifier.weight(1f)) { onDigit("1") }
                CalcBtn("2", Modifier.weight(1f)) { onDigit("2") }
                CalcBtn("3", Modifier.weight(1f)) { onDigit("3") }
                CalcBtn("+", Modifier.weight(1f), OpColor) { onOp("+") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("0", Modifier.weight(2f)) { onDigit("0") }
                CalcBtn(".", Modifier.weight(1f)) { onDot() }
                CalcBtn("=", Modifier.weight(1f), OpColor) { onEq() }
            }
        }
    }
}

@Composable
fun DartCell(dart: Dart, modifier: Modifier, onClick: (Int) -> Unit) {
    Box(
        modifier = modifier.fillMaxHeight()
            .clip(RoundedCornerShape(8.dp)).background(TileBg)
            .clickable { onClick(dart.score) },
        contentAlignment = Alignment.Center
    ) {
        Text(dart.toString(), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun CalcBtn(label: String, modifier: Modifier, color: Color = TileBgDark, onClick: () -> Unit) {
    Box(
        modifier = modifier.fillMaxHeight()
            .clip(RoundedCornerShape(12.dp)).background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun BigButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(24.dp)).background(TileBg).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 34.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
    }
}

@Composable
fun SmallButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(18.dp)).background(TileBgDark).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Accent, textAlign = TextAlign.Center)
    }
}

@Composable
fun PlaceholderScreen(title: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Text("Экран в разработке", fontSize = 16.sp, color = Accent)
        Spacer(Modifier.height(48.dp))
        Box(
            modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(TileBg)
                .clickable { onBack() }.padding(horizontal = 32.dp, vertical = 14.dp)
        ) { Text("← Назад", color = Color.White, fontSize = 16.sp) }
    }
}

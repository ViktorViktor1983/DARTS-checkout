package com.example.dartscheckout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.dartscheckout.data.Dart
import com.example.dartscheckout.data.fmtNumber
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.OpColor
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

@Composable
fun CalculatorScreen(onBack: () -> Unit) {
    var display by remember { mutableStateOf("0") }
    var sequence by remember { mutableStateOf<List<String>>(emptyList()) }
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
        sequence = emptyList()
        if (replaceOnNext) { display = d; replaceOnNext = false }
        else display = if (display == "0") d else display + d
    }
    fun onDot() {
        sequence = emptyList()
        if (replaceOnNext) { display = "0."; replaceOnNext = false }
        else if (!display.contains(".")) display += "."
    }
    fun onDart(dart: Dart) {
        val cur = display.toDoubleOrNull() ?: 0.0
        display = fmtNumber(cur + dart.score)
        sequence = sequence + dart.toString()
        replaceOnNext = true
    }
    fun onOp(op: String) {
        sequence = emptyList()
        if (pendingOp != null && !replaceOnNext) compute()
        accumulator = display.toDoubleOrNull() ?: 0.0
        pendingOp = op
        replaceOnNext = true
    }
    fun onEq() { sequence = emptyList(); if (pendingOp != null) compute() }
    fun onClear() { display = "0"; sequence = emptyList(); accumulator = null; pendingOp = null; replaceOnNext = true }
    fun onBackspace() {
        sequence = emptyList()
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
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp)
                .clip(RoundedCornerShape(14.dp)).background(TileBgDark)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sequence.joinToString("+"),
                color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f), maxLines = 3, softWrap = true
            )
            Text(
                text = display,
                color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold,
                maxLines = 1, textAlign = TextAlign.End
            )
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
                    DartCell(Dart(n, 3), Modifier.weight(1f)) { onDart(it) }
                    DartCell(Dart(n, 1), Modifier.weight(1f)) { onDart(it) }
                    DartCell(Dart(n, 2), Modifier.weight(1f)) { onDart(it) }
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
                    sequence = emptyList()
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
fun DartCell(dart: Dart, modifier: Modifier, onClick: (Dart) -> Unit) {
    Box(
        modifier = modifier.fillMaxHeight()
            .clip(RoundedCornerShape(8.dp)).background(TileBg)
            .clickable { onClick(dart) },
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

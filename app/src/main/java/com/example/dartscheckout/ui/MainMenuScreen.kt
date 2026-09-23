package com.example.dartscheckout.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dartscheckout.theme.GoldAccent
import com.example.dartscheckout.ui.components.RangeButton
import com.example.dartscheckout.ui.components.SmallButton

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
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent,
            letterSpacing = 3.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RangeButton(60, 99, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(60..99) }
            RangeButton(100, 134, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(100..134) }
        }
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RangeButton(135, 170, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(135..170) }
            Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallButton("Калькулятор", Modifier.fillMaxWidth().weight(1f), onCalculator)
                SmallButton("Настройки\nи инструкция", Modifier.fillMaxWidth().weight(1f), onSettings)
            }
        }
    }
}

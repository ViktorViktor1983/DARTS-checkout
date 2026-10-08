package com.example.dartscheckout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    onHelp: () -> Unit,
    onCalculator: () -> Unit,
    onMyCheckouts: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Darts Checkout",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp)
        )

        // Золотая кнопка «Мои закрытые чекауты»
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(GoldAccent)
                .clickable { onMyCheckouts() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "🏆  Мои закрытые чекауты",
                color = Color(0xFF121212),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RangeButton(60, 99, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(60..99) }
            RangeButton(100, 134, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(100..134) }
        }

        Row(
            Modifier.fillMaxWidth().weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RangeButton(135, 170, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(135..170) }
            Column(
                Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SmallButton("Калькулятор", Modifier.fillMaxWidth().weight(1f), onCalculator)
                SmallButton("Настройки", Modifier.fillMaxWidth().weight(1f), onSettings)
                SmallButton("Помощь", Modifier.fillMaxWidth().weight(1f), onHelp)
            }
        }
    }
}

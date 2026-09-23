package com.example.dartscheckout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.ErrorColor
import com.example.dartscheckout.theme.GoldAccent
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    sortDescending: Boolean,
    onSortDescendingChange: (Boolean) -> Unit,
    onExport: () -> Unit,
    onImport: () -> Unit,
    onReset: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text("Настройки и инструкция", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("───────  ✦  ───────", color = Accent, fontSize = 16.sp)
            Spacer(Modifier.height(20.dp))
            Text("РАЗРАБОТЧИК", color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Medium, letterSpacing = 4.sp)
            Spacer(Modifier.height(12.dp))
            Text("Лодкин Виктор", color = GoldAccent, fontSize = 30.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text("Евгеньевич", color = GoldAccent, fontSize = 30.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(20.dp))
            Text("───────  ✦  ───────", color = Accent, fontSize = 16.sp)

            Spacer(Modifier.height(36.dp))

            // Порядок чисел
            Text("ПОРЯДОК ЧИСЕЛ", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Medium, letterSpacing = 3.sp)
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SortButton(
                    label = "По возрастанию",
                    selected = !sortDescending,
                    modifier = Modifier.weight(1f),
                    onClick = { onSortDescendingChange(false) }
                )
                SortButton(
                    label = "По убыванию",
                    selected = sortDescending,
                    modifier = Modifier.weight(1f),
                    onClick = { onSortDescendingChange(true) }
                )
            }

            Spacer(Modifier.height(36.dp))

            // Дополнительно
            Text("ДОПОЛНИТЕЛЬНО", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Medium, letterSpacing = 3.sp)
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionButton(
                    label = "Экспорт",
                    modifier = Modifier.weight(1f),
                    onClick = onExport
                )
                ActionButton(
                    label = "Импорт",
                    modifier = Modifier.weight(1f),
                    onClick = onImport
                )
            }
            Spacer(Modifier.height(12.dp))
            ActionButton(
                label = "Сброс к заводским",
                modifier = Modifier.fillMaxWidth(),
                color = ErrorColor,
                onClick = onReset
            )
            Spacer(Modifier.height(40.dp))
        }

        Text(
            "Darts Checkout v1.0",
            color = TileBg,
            fontSize = 13.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SortButton(
    label: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val bg = if (selected) Accent else TileBg
    val fg = if (selected) Color(0xFF121212) else Color.White
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            color = fg,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ActionButton(
    label: String,
    modifier: Modifier,
    color: Color = TileBg,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(color)
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}

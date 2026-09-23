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
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.GoldAccent
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text("Настройки и инструкция", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
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
        }
        Spacer(Modifier.weight(1f))
        Text("Darts Checkout v1.0", color = TileBg, fontSize = 13.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

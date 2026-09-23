package com.example.dartscheckout.ui.components

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
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

@Composable
fun RangeButton(start: Int, end: Int, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(TileBg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(start.toString(), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text("↓", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = Accent)
            Text(end.toString(), fontSize = 48.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
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

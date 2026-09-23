package com.example.dartscheckout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.IMPOSSIBLE_NUMBERS
import com.example.dartscheckout.theme.ImpossibleBg
import com.example.dartscheckout.theme.ImpossibleText
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

@Composable
fun RangeScreen(
    range: IntRange,
    descending: Boolean,
    onNumberClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val numbers = remember(range, descending) {
        val list = range.toList()
        if (descending) list.reversed() else list
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text(
                "${range.first}–${range.last}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
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
    val impossible = number in IMPOSSIBLE_NUMBERS
    val bg = if (impossible) ImpossibleBg else TileBg
    val fg = if (impossible) ImpossibleText else Color.White
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .then(if (impossible) Modifier else Modifier.clickable { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        Text(number.toString(), fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = fg)
    }
}

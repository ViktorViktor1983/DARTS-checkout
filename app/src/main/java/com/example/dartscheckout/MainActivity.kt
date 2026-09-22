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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBg
                ) {
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
            onRangeClick = { range ->
                selectedRange = range
                screen = "range"
            },
            onSettings = { screen = "settings" },
            onCalculator = { screen = "calc" }
        )
        screen == "range" -> RangeScreen(
            range = selectedRange,
            onNumberClick = { n ->
                selectedNumber = n
                screen = "number"
            },
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
        screen == "calc" -> PlaceholderScreen(
            title = "Калькулятор",
            onBack = { screen = "main" }
        )
    }
}

@Composable
fun MainMenuScreen(
    onRangeClick: (IntRange) -> Unit,
    onSettings: () -> Unit,
    onCalculator: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Darts Checkout",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Верхний ряд: 60–99 | 100–134
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BigButton("60–99", Modifier.weight(1f).fillMaxHeight()) { onRangeClick(60..99) }
            BigButton("100–134", Modifier.weight(1f).fillMaxHeight()) { onRangeClick(100..134) }
        }

        // Нижний ряд: 135–170 | (Калькулятор / Настройки)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BigButton("135–170", Modifier.weight(1f).fillMaxHeight()) { onRangeClick(135..170) }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallButton(
                    label = "Калькулятор",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = onCalculator
                )
                SmallButton(
                    label = "Настройки\nи инструкция",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = onSettings
                )
            }
        }
    }
}

@Composable
fun RangeScreen(
    range: IntRange,
    onNumberClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val numbers = remember(range) { range.toList() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(TileBgDark)
                    .clickable { onBack() }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text("← Назад", color = Accent, fontSize = 15.sp)
            }
            Spacer(Modifier.width(12.dp))
            Text(
                text = "${range.first}–${range.last}",
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
        ) {
            items(numbers) { n ->
                NumberTile(n) { onNumberClick(n) }
            }
        }
    }
}

@Composable
fun NumberTile(number: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(14.dp))
            .background(TileBg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun BigButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(TileBg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SmallButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(TileBgDark)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Accent,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun PlaceholderScreen(title: String, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Экран в разработке",
            fontSize = 16.sp,
            color = Accent
        )
        Spacer(Modifier.height(48.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(TileBg)
                .clickable { onBack() }
                .padding(horizontal = 32.dp, vertical = 14.dp)
        ) {
            Text("← Назад", color = Color.White, fontSize = 16.sp)
        }
    }
}

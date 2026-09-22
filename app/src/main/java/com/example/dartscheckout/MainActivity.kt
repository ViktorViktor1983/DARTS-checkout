package com.example.dartscheckout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

    when {
        screen == "main" -> MainMenuScreen(
            onRangeClick = { screen = "range:$it" },
            onSettings = { screen = "settings" },
            onCalculator = { screen = "calc" }
        )
        screen == "settings" -> PlaceholderScreen(
            title = "Настройки и инструкция",
            onBack = { screen = "main" }
        )
        screen == "calc" -> PlaceholderScreen(
            title = "Калькулятор",
            onBack = { screen = "main" }
        )
        screen.startsWith("range:") -> {
            val range = screen.removePrefix("range:")
            PlaceholderScreen(
                title = "Диапазон $range",
                onBack = { screen = "main" }
            )
        }
    }
}

@Composable
fun MainMenuScreen(
    onRangeClick: (String) -> Unit,
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

        // 4 больших квадрата 2x2
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BigButton("60–89", Modifier.weight(1f).fillMaxHeight()) { onRangeClick("60-89") }
            BigButton("90–120", Modifier.weight(1f).fillMaxHeight()) { onRangeClick("90-120") }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BigButton("121–140", Modifier.weight(1f).fillMaxHeight()) { onRangeClick("121-140") }
            BigButton("141–170", Modifier.weight(1f).fillMaxHeight()) { onRangeClick("141-170") }
        }

        // 2 кнопки снизу
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallButton(
                label = "Настройки\nи инструкция",
                modifier = Modifier.weight(1f).fillMaxHeight(),
                onClick = onSettings
            )
            SmallButton(
                label = "Калькулятор",
                modifier = Modifier.weight(1f).fillMaxHeight(),
                onClick = onCalculator
            )
        }
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

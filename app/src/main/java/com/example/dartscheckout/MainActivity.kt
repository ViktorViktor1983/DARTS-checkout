package com.example.dartscheckout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import com.example.dartscheckout.data.CheckoutVariant
import com.example.dartscheckout.data.DEFAULT_CHECKOUTS
import com.example.dartscheckout.theme.DarkBg
import com.example.dartscheckout.ui.*

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
    var checkouts by remember { mutableStateOf(DEFAULT_CHECKOUTS) }
    var editIndex by remember { mutableStateOf(-1) }

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
        screen == "number" -> {
            val num = selectedNumber
            if (num != null) {
                NumberScreen(
                    number = num,
                    variants = checkouts[num].orEmpty(),
                    onEditVariant = { idx -> editIndex = idx; screen = "edit" },
                    onAddVariant = { editIndex = -1; screen = "edit" },
                    onDelete = { idx ->
                        val list = (checkouts[num] ?: emptyList()).toMutableList()
                        if (idx in list.indices) {
                            list.removeAt(idx)
                            checkouts = checkouts + (num to list)
                        }
                    },
                    onMakeMain = { idx ->
                        val list = (checkouts[num] ?: emptyList()).toMutableList()
                        if (idx in list.indices) {
                            for (i in list.indices) {
                                if (list[i].label.startsWith("Основной")) {
                                    list[i] = list[i].copy(label = "Альтернативный")
                                }
                            }
                            list[idx] = list[idx].copy(label = "Основной")
                            checkouts = checkouts + (num to list)
                        }
                    },
                    onMoveUp = { idx ->
                        val list = (checkouts[num] ?: emptyList()).toMutableList()
                        if (idx > 0 && idx < list.size) {
                            val tmp = list[idx]
                            list[idx] = list[idx - 1]
                            list[idx - 1] = tmp
                            checkouts = checkouts + (num to list)
                        }
                    },
                    onMoveDown = { idx ->
                        val list = (checkouts[num] ?: emptyList()).toMutableList()
                        if (idx >= 0 && idx < list.size - 1) {
                            val tmp = list[idx]
                            list[idx] = list[idx + 1]
                            list[idx + 1] = tmp
                            checkouts = checkouts + (num to list)
                        }
                    },
                    onBack = { screen = "range" }
                )
            }
        }
        screen == "edit" -> {
            val num = selectedNumber
            if (num != null) {
                val initial = if (editIndex >= 0) checkouts[num]?.getOrNull(editIndex) else null
                EditVariantScreen(
                    number = num,
                    initial = initial,
                    onSave = { newVariant: CheckoutVariant ->
                        val list = (checkouts[num] ?: emptyList()).toMutableList()
                        if (editIndex == -1) {
                            list.add(newVariant)
                        } else if (editIndex in list.indices) {
                            list[editIndex] = newVariant
                        }
                        checkouts = checkouts + (num to list)
                        screen = "number"
                    },
                    onCancel = { screen = "number" }
                )
            }
        }
        screen == "settings" -> SettingsScreen(onBack = { screen = "main" })
        screen == "calc" -> CalculatorScreen(onBack = { screen = "main" })
    }
}

package com.example.dartscheckout.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.GoldAccent
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

private const val VK_URL = "https://vk.ru/hvastun1"
private const val SUPPORT_PHONE = "8 999 220-38-11"
private const val SUPPORT_BANK = "Т-Банк (СБП)"

@Composable
fun HelpScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var showInstruction by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Верхняя панель
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text("Помощь / Поддержка", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Подпись разработчика
            Text("───────  ✦  ───────", color = Accent, fontSize = 16.sp)
            Spacer(Modifier.height(16.dp))
            Text("РАЗРАБОТЧИК", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Medium, letterSpacing = 4.sp)
            Spacer(Modifier.height(10.dp))
            Text("Лодкин Виктор", color = GoldAccent, fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text("Евгеньевич", color = GoldAccent, fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(16.dp))
            Text("───────  ✦  ───────", color = Accent, fontSize = 16.sp)

            Spacer(Modifier.height(36.dp))

            // Кнопка инструкции
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Accent)
                    .clickable { showInstruction = true }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Инструкция по использованию",
                    color = Color(0xFF121212),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(36.dp))

            // Блок поддержки
            Text("ПОДДЕРЖКА", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Medium, letterSpacing = 3.sp)
            Spacer(Modifier.height(12.dp))

            Text(
                "По любым вопросам обращайтесь к разработчику",
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(16.dp))

            // ВК
            ContactCard(
                title = "ВКонтакте",
                value = VK_URL,
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(VK_URL))
                        context.startActivity(intent)
                    } catch (_: Exception) { }
                }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                "Если хотите отблагодарить за приложение — можно перевести любую сумму. Даже 10 ₽ очень помогут в будущих проектах.",
                color = Color.White,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Телефон для перевода
            ContactCard(
                title = "Перевод по СБП",
                value = "$SUPPORT_PHONE\n$SUPPORT_BANK",
                onClick = { }
            )

            Spacer(Modifier.height(32.dp))
        }
    }

    if (showInstruction) {
        InstructionDialog(onDismiss = { showInstruction = false })
    }
}

@Composable
private fun ContactCard(title: String, value: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(TileBg)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column {
            Text(title, color = Accent, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 2.sp)
            Spacer(Modifier.height(6.dp))
            Text(value, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun InstructionDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(TileBgDark)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(TileBg)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✕", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "Как пользоваться",
                    color = Accent,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    InstructionText()
                }

                Spacer(Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Accent)
                        .clickable { onDismiss() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Понятно", color = Color(0xFF121212), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun InstructionText() {
    val paragraphs = listOf(
        "ГЛАВНЫЙ ЭКРАН" to "Выберите диапазон — 60–89, 90–119, 120–139 или 140–170. Откроется сетка чисел. Числа розового цвета (159, 162, 163, 165, 166, 168, 169) невозможно закрыть за три дротика — они не нажимаются.",
        "ЗАКРЫТИЕ ЧИСЛА" to "Нажмите на число — увидите варианты закрытия. Каждый вариант — последовательность из 1–3 дротиков. У вариантов есть метки:\n• Основной — рекомендованный путь\n• Альтернативный — другой удобный путь\n• Запасной — резервный вариант\n• Промах T17 — что делать, если вместо утроения попали в сектор.",
        "РЕДАКТИРОВАНИЕ" to "На карточке нажмите ⋮ — откроется меню: редактировать, сделать основным, переместить вверх/вниз, удалить. Внизу экрана числа — кнопка «+ Добавить вариант» для своих закрытий.",
        "КАЛЬКУЛЯТОР" to "Считает сумму бросков. Нажимайте дротики (T20, S19, D5) — они складываются. Слева — последовательность, справа — сумма. Работает и как обычный калькулятор.",
        "НАСТРОЙКИ" to "Порядок чисел (возрастание / убывание), экспорт и импорт базы (для переноса между устройствами), сброс к заводским."
    )

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        paragraphs.forEach { (title, body) ->
            Column {
                Text(
                    title,
                    color = Accent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(body, color = Color.White, fontSize = 14.sp, lineHeight = 20.sp)
            }
        }
    }
}

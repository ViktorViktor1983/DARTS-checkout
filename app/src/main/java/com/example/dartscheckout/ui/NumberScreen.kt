package com.example.dartscheckout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dartscheckout.data.CheckoutVariant
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.AltVariantColor
import com.example.dartscheckout.theme.BackupVariantColor
import com.example.dartscheckout.theme.ErrorColor
import com.example.dartscheckout.theme.MainVariantColor
import com.example.dartscheckout.theme.MissVariantColor
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

@Composable
fun NumberScreen(
    number: Int,
    variants: List<CheckoutVariant>,
    onEditVariant: (Int) -> Unit,
    onAddVariant: () -> Unit,
    onDelete: (Int) -> Unit,
    onMakeMain: (Int) -> Unit,
    onMoveUp: (Int) -> Unit,
    onMoveDown: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text("Закрытие $number", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (variants.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Вариантов пока нет",
                        color = Accent,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                variants.forEachIndexed { idx, v ->
                    CheckoutCardWithMenu(
                        variant = v,
                        onEdit = { onEditVariant(idx) },
                        onDelete = { onDelete(idx) },
                        onMakeMain = { onMakeMain(idx) },
                        onMoveUp = { onMoveUp(idx) },
                        onMoveDown = { onMoveDown(idx) }
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(TileBg)
                    .clickable { onAddVariant() }
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("+  Добавить вариант", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun CheckoutCardWithMenu(
    variant: CheckoutVariant,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onMakeMain: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
) {
    var menuOpen by remember { mutableStateOf(false) }
    val color = when {
        variant.label.startsWith("Основной") -> MainVariantColor
        variant.label.startsWith("Альтернативный") -> AltVariantColor
        variant.label.startsWith("Запасной") -> BackupVariantColor
        else -> MissVariantColor
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(TileBgDark)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f).padding(horizontal = 12.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = variant.label.uppercase(),
                    color = Accent,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = variant.throws.joinToString("   "),
                    color = color,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(44.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { menuOpen = true },
                contentAlignment = Alignment.Center
            ) {
                Text("⋮", color = Accent, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
        }
        DropdownMenu(
            expanded = menuOpen,
            onDismissRequest = { menuOpen = false }
        ) {
            DropdownMenuItem(text = { Text("Редактировать") }, onClick = { menuOpen = false; onEdit() })
            DropdownMenuItem(text = { Text("Сделать основным") }, onClick = { menuOpen = false; onMakeMain() })
            DropdownMenuItem(text = { Text("Вверх") }, onClick = { menuOpen = false; onMoveUp() })
            DropdownMenuItem(text = { Text("Вниз") }, onClick = { menuOpen = false; onMoveDown() })
            DropdownMenuItem(text = { Text("Удалить", color = ErrorColor) }, onClick = { menuOpen = false; onDelete() })
        }
    }
}

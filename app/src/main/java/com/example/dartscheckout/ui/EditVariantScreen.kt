package com.example.dartscheckout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dartscheckout.data.CheckoutVariant
import com.example.dartscheckout.data.Dart
import com.example.dartscheckout.data.sumOfDarts
import com.example.dartscheckout.theme.Accent
import com.example.dartscheckout.theme.ErrorColor
import com.example.dartscheckout.theme.TileBg
import com.example.dartscheckout.theme.TileBgDark

@Composable
fun EditVariantScreen(
    number: Int,
    initial: CheckoutVariant?,
    onSave: (CheckoutVariant) -> Unit,
    onCancel: () -> Unit
) {
    var label by remember { mutableStateOf(initial?.label ?: "") }
    val initialSlots = remember {
        val base = initial?.throws ?: emptyList()
        val list = base.toMutableList()
        while (list.size < 3) list.add("")
        list.take(3)
    }
    var slots by remember { mutableStateOf(initialSlots) }
    var pickerSlot by remember { mutableStateOf(-1) }

    val filled = slots.filter { it.isNotEmpty() }
    val sum = sumOfDarts(filled)
    val valid = filled.isNotEmpty() && sum == number

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onCancel() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text(
                if (initial == null) "Новый вариант — $number" else "Правка — $number",
                fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White
            )
        }

        Spacer(Modifier.height(20.dp))

        Text("Название варианта", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = label,
            onValueChange = { label = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Например: Основной / Промах T17", color = TileBg) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Accent,
                unfocusedBorderColor = TileBg,
                cursorColor = Accent
            )
        )

        Spacer(Modifier.height(20.dp))

        Text("Дротики (до 3):", color = Accent, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            for (i in 0..2) {
                SlotEdit(
                    value = slots[i],
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    onClick = { pickerSlot = i }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Сумма: $sum / $number",
            color = if (sum == number) Accent else ErrorColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        if (filled.isNotEmpty() && sum != number) {
            Spacer(Modifier.height(4.dp))
            Text(
                "Сумма дротиков должна быть ровно $number",
                color = ErrorColor,
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.weight(1f))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Отмена", color = Accent)
            }
            Button(
                onClick = {
                    val finalLabel = label.ifBlank {
                        if (initial == null) "Без названия" else initial.label
                    }
                    onSave(CheckoutVariant(finalLabel, filled))
                },
                enabled = valid,
                modifier = Modifier.weight(1f)
            ) {
                Text("Сохранить")
            }
        }
    }

    if (pickerSlot >= 0) {
        DartPickerDialog(
            onPick = { dart ->
                val newSlots = slots.toMutableList()
                newSlots[pickerSlot] = dart.toString()
                slots = newSlots
                pickerSlot = -1
            },
            onClear = {
                val newSlots = slots.toMutableList()
                newSlots[pickerSlot] = ""
                slots = newSlots
                pickerSlot = -1
            },
            onDismiss = { pickerSlot = -1 }
        )
    }
}

@Composable
fun SlotEdit(value: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (value.isEmpty()) TileBgDark else TileBg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.ifEmpty { "—" },
            color = if (value.isEmpty()) TileBg else Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DartPickerDialog(
    onPick: (Dart) -> Unit,
    onClear: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onClear) { Text("Очистить", color = ErrorColor) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена", color = Accent) }
        },
        title = { Text("Выберите дротик", color = Color.White) },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.height(360.dp)
            ) {
                items(Dart.ALL) { dart ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(TileBg)
                            .clickable { onPick(dart) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(dart.toString(), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    )
}

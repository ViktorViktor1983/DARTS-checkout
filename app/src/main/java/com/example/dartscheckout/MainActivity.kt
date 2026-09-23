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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
val OpColor = Color(0xFF0288D1)
val GoldAccent = Color(0xFFFFD54F)
val MainVariantColor = Color(0xFF4FC3F7)
val AltVariantColor = Color(0xFFFFFFFF)
val BackupVariantColor = Color(0xFFB39DDB)
val MissVariantColor = Color(0xFFFFAB91)
val ImpossibleBg = Color(0xFF5C2B3A)
val ImpossibleText = Color(0xFFEF9A9A)

val IMPOSSIBLE_NUMBERS = setOf(159, 162, 163, 165, 166, 168, 169)

data class Dart(val sector: Int, val multiplier: Int) {
    val score: Int get() = sector * multiplier
    override fun toString(): String = when {
        sector == 25 && multiplier == 2 -> "BULL"
        sector == 25 -> "25"
        multiplier == 1 -> "S$sector"
        multiplier == 2 -> "D$sector"
        else -> "T$sector"
    }
}

data class CheckoutVariant(val label: String, val throws: List<String>)

val CHECKOUTS: Map<Int, List<CheckoutVariant>> = mapOf(
    60 to listOf(CheckoutVariant("Основной", listOf("S20", "D20"))),
    61 to listOf(
        CheckoutVariant("Основной", listOf("T15", "D8")),
        CheckoutVariant("Промах T15", listOf("S15", "S6", "D20")),
        CheckoutVariant("Альтернативный", listOf("25", "D18")),
        CheckoutVariant("Запасной", listOf("T11", "D14")),
        CheckoutVariant("Промах T11", listOf("S11", "S10", "D20"))
    ),
    62 to listOf(
        CheckoutVariant("Основной", listOf("T10", "D16")),
        CheckoutVariant("Промах T10", listOf("S10", "S12", "D20")),
        CheckoutVariant("Альтернативный", listOf("T18", "D4")),
        CheckoutVariant("Промах T18", listOf("S18", "S4", "D20"))
    ),
    63 to listOf(
        CheckoutVariant("Основной", listOf("T13", "D12")),
        CheckoutVariant("Промах T13", listOf("S13", "S10", "D20")),
        CheckoutVariant("Альтернативный", listOf("T17", "D6")),
        CheckoutVariant("Промах T17", listOf("S17", "S6", "D20"))
    ),
    64 to listOf(
        CheckoutVariant("Основной", listOf("T16", "D8")),
        CheckoutVariant("Промах T16", listOf("S16", "S8", "D20")),
        CheckoutVariant("Альтернативный", listOf("T8", "D20")),
        CheckoutVariant("Промах T8", listOf("S8", "S16", "D20"))
    ),
    65 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D4")),
        CheckoutVariant("Промах T19", listOf("S19", "S6", "D20")),
        CheckoutVariant("Альтернативный", listOf("25", "D20")),
        CheckoutVariant("Запасной", listOf("T11", "D16")),
        CheckoutVariant("Промах T11", listOf("S11", "S14", "D20"))
    ),
    66 to listOf(
        CheckoutVariant("Основной", listOf("T14", "D12")),
        CheckoutVariant("Промах T14", listOf("S14", "S12", "D20")),
        CheckoutVariant("Альтернативный", listOf("T10", "D18")),
        CheckoutVariant("Промах T10", listOf("S10", "S16", "D20"))
    ),
    67 to listOf(
        CheckoutVariant("Основной", listOf("T17", "D8")),
        CheckoutVariant("Промах T17", listOf("S17", "S10", "D20")),
        CheckoutVariant("Альтернативный", listOf("T9", "D20")),
        CheckoutVariant("Промах T9", listOf("S9", "S18", "D20"))
    ),
    68 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D4")),
        CheckoutVariant("Промах T20", listOf("S20", "S8", "D20")),
        CheckoutVariant("Альтернативный", listOf("T16", "D10")),
        CheckoutVariant("Промах T16", listOf("S16", "S12", "D20"))
    ),
    69 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D6")),
        CheckoutVariant("Промах T19", listOf("S19", "S10", "D20")),
        CheckoutVariant("Альтернативный", listOf("T15", "D12")),
        CheckoutVariant("Промах T15", listOf("S15", "S14", "D20"))
    ),
    70 to listOf(
        CheckoutVariant("Основной", listOf("T18", "D8")),
        CheckoutVariant("Промах T18", listOf("S18", "S12", "D20")),
        CheckoutVariant("Альтернативный", listOf("T10", "D20")),
        CheckoutVariant("Промах T10", listOf("S10", "S20", "D20"))
    ),
    71 to listOf(
        CheckoutVariant("Основной", listOf("T13", "D16")),
        CheckoutVariant("Промах T13", listOf("S13", "S18", "D20")),
        CheckoutVariant("Альтернативный", listOf("T17", "D10")),
        CheckoutVariant("Промах T17", listOf("S17", "S14", "D20"))
    ),
    72 to listOf(
        CheckoutVariant("Основной", listOf("T16", "D12")),
        CheckoutVariant("Промах T16", listOf("S16", "S16", "D20")),
        CheckoutVariant("Альтернативный", listOf("T12", "D18")),
        CheckoutVariant("Промах T12", listOf("S12", "S20", "D20"))
    ),
    73 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D8")),
        CheckoutVariant("Промах T19", listOf("S19", "S14", "D20")),
        CheckoutVariant("Альтернативный", listOf("T15", "D14")),
        CheckoutVariant("Промах T15", listOf("S15", "S18", "D20"))
    ),
    74 to listOf(
        CheckoutVariant("Основной", listOf("T14", "D16")),
        CheckoutVariant("Промах T14", listOf("S14", "S20", "D20")),
        CheckoutVariant("Альтернативный", listOf("T18", "D10")),
        CheckoutVariant("Промах T18", listOf("S18", "S16", "D20"))
    ),
    75 to listOf(
        CheckoutVariant("Основной", listOf("T17", "D12")),
        CheckoutVariant("Промах T17", listOf("S17", "S18", "D20")),
        CheckoutVariant("Альтернативный", listOf("T13", "D18")),
        CheckoutVariant("Промах T13", listOf("S13", "T14", "D10"))
    ),
    76 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D8")),
        CheckoutVariant("Промах T20", listOf("S20", "S16", "D20")),
        CheckoutVariant("Альтернативный", listOf("T16", "D14")),
        CheckoutVariant("Промах T16", listOf("S16", "S20", "D20"))
    ),
    77 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D10")),
        CheckoutVariant("Промах T19", listOf("S19", "S18", "D20")),
        CheckoutVariant("Альтернативный", listOf("T15", "D16")),
        CheckoutVariant("Промах T15", listOf("S15", "T14", "D10"))
    ),
    78 to listOf(
        CheckoutVariant("Основной", listOf("T18", "D12")),
        CheckoutVariant("Промах T18", listOf("S18", "S20", "D20")),
        CheckoutVariant("Альтернативный", listOf("T14", "D18")),
        CheckoutVariant("Промах T14", listOf("S14", "T16", "D8"))
    ),
    79 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D11")),
        CheckoutVariant("Промах T19", listOf("S19", "S20", "D20")),
        CheckoutVariant("Альтернативный", listOf("T13", "D20")),
        CheckoutVariant("Промах T13", listOf("S13", "T14", "D12"))
    ),
    80 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D10")),
        CheckoutVariant("Промах T20", listOf("S20", "S20", "D20")),
        CheckoutVariant("Альтернативный", listOf("T16", "D16")),
        CheckoutVariant("Промах T16", listOf("S16", "T16", "D8"))
    ),
    81 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D12")),
        CheckoutVariant("Промах T19", listOf("S19", "T14", "D10")),
        CheckoutVariant("Альтернативный", listOf("T15", "D18")),
        CheckoutVariant("Промах T15", listOf("S15", "T14", "D12"))
    ),
    82 to listOf(
        CheckoutVariant("Основной", listOf("T14", "D20")),
        CheckoutVariant("Промах T14", listOf("S14", "T20", "D4")),
        CheckoutVariant("Альтернативный", listOf("BULL", "D16"))
    ),
    83 to listOf(
        CheckoutVariant("Основной", listOf("T17", "D16")),
        CheckoutVariant("Промах T17", listOf("S17", "T14", "D12"))
    ),
    84 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D12")),
        CheckoutVariant("Промах T20", listOf("S20", "T16", "D8")),
        CheckoutVariant("Альтернативный", listOf("T16", "D18")),
        CheckoutVariant("Промах T16", listOf("S16", "T20", "D4"))
    ),
    85 to listOf(
        CheckoutVariant("Основной", listOf("T15", "D20")),
        CheckoutVariant("Промах T15", listOf("S15", "T18", "D8")),
        CheckoutVariant("Альтернативный", listOf("T19", "D14")),
        CheckoutVariant("Промах T19", listOf("S19", "T14", "D12"))
    ),
    86 to listOf(
        CheckoutVariant("Основной", listOf("T18", "D16")),
        CheckoutVariant("Промах T18", listOf("S18", "T20", "D4"))
    ),
    87 to listOf(
        CheckoutVariant("Основной", listOf("T17", "D18")),
        CheckoutVariant("Промах T17", listOf("S17", "T18", "D8"))
    ),
    88 to listOf(
        CheckoutVariant("Основной", listOf("T16", "D20")),
        CheckoutVariant("Промах T16", listOf("S16", "T16", "D12")),
        CheckoutVariant("Альтернативный", listOf("T20", "D14")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D4"))
    ),
    89 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D16")),
        CheckoutVariant("Промах T19", listOf("S19", "T18", "D8")),
        CheckoutVariant("Альтернативный", listOf("T17", "D19")),
        CheckoutVariant("Промах T17", listOf("S17", "T16", "D12"))
    ),
    90 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D15")),
        CheckoutVariant("Промах T20", listOf("S20", "T18", "D8")),
        CheckoutVariant("Альтернативный", listOf("T18", "D18")),
        CheckoutVariant("Промах T18", listOf("S18", "T16", "D12"))
    ),
    91 to listOf(
        CheckoutVariant("Основной", listOf("T17", "D20")),
        CheckoutVariant("Промах T17", listOf("S17", "T14", "D16")),
        CheckoutVariant("Альтернативный", listOf("T19", "D17")),
        CheckoutVariant("Промах T19", listOf("S19", "T16", "D12"))
    ),
    92 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T16", "D12"))
    ),
    93 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D18")),
        CheckoutVariant("Промах T19", listOf("S19", "T14", "D16"))
    ),
    94 to listOf(
        CheckoutVariant("Основной", listOf("T18", "D20")),
        CheckoutVariant("Промах T18", listOf("S18", "T20", "D8"))
    ),
    95 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D19")),
        CheckoutVariant("Промах T19", listOf("S19", "T20", "D8"))
    ),
    96 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D18")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D8"))
    ),
    97 to listOf(
        CheckoutVariant("Основной", listOf("T19", "D20")),
        CheckoutVariant("Промах T19", listOf("S19", "T18", "D12"))
    ),
    98 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D19")),
        CheckoutVariant("Промах T20", listOf("S20", "T18", "D12"))
    ),
    99 to listOf(
        CheckoutVariant("Основной", listOf("T19", "S10", "D16")),
        CheckoutVariant("Промах T19", listOf("S19", "T20", "D10")),
        CheckoutVariant("Альтернативный", listOf("T20", "S7", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T19", "D11"))
    ),
    100 to listOf(
        CheckoutVariant("Основной", listOf("T20", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D10"))
    ),
    101 to listOf(
        CheckoutVariant("Основной", listOf("T17", "BULL")),
        CheckoutVariant("Промах T17", listOf("S17", "T20", "D12")),
        CheckoutVariant("Альтернативный", listOf("T20", "S9", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T19", "D12"))
    ),
    102 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S10", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T14", "D20"))
    ),
    103 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S11", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T17", "D16"))
    ),
    104 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S12", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D12")),
        CheckoutVariant("Альтернативный", listOf("T18", "S18", "D16")),
        CheckoutVariant("Промах T18", listOf("S18", "T18", "D16"))
    ),
    105 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S13", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T15", "D20"))
    ),
    106 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S14", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T18", "D16"))
    ),
    107 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S15", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T17", "D18")),
        CheckoutVariant("Альтернативный", listOf("T19", "S18", "D16")),
        CheckoutVariant("Промах T19", listOf("S19", "T16", "D20"))
    ),
    108 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S16", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T16", "D20"))
    ),
    109 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S17", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T19", "D16")),
        CheckoutVariant("Альтернативный", listOf("T19", "S20", "D16")),
        CheckoutVariant("Промах T19", listOf("S19", "T20", "D15"))
    ),
    110 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S18", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D15")),
        CheckoutVariant("Альтернативный", listOf("T20", "BULL"))
    ),
    111 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S19", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T17", "D20"))
    ),
    112 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S20", "D16")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D16")),
        CheckoutVariant("Альтернативный", listOf("T20", "T12", "D8"))
    ),
    113 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S13", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T19", "D18")),
        CheckoutVariant("Альтернативный", listOf("T20", "T13", "D7"))
    ),
    114 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S14", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T18", "D20")),
        CheckoutVariant("Альтернативный", listOf("T20", "T14", "D6"))
    ),
    115 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S15", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T19", "D19")),
        CheckoutVariant("Альтернативный", listOf("T20", "T15", "D5"))
    ),
    116 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S16", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D18")),
        CheckoutVariant("Альтернативный", listOf("T20", "T16", "D4"))
    ),
    117 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S17", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T19", "D20")),
        CheckoutVariant("Альтернативный", listOf("T20", "T17", "D3"))
    ),
    118 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S18", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D19")),
        CheckoutVariant("Альтернативный", listOf("T20", "T18", "D2"))
    ),
    119 to listOf(
        CheckoutVariant("Основной", listOf("T19", "T12", "D13")),
        CheckoutVariant("Промах T19", listOf("S19", "T20", "D20")),
        CheckoutVariant("Альтернативный", listOf("T20", "S19", "D20"))
    ),
    120 to listOf(
        CheckoutVariant("Основной", listOf("T20", "S20", "D20")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "D20"))
    ),
    121 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T11", "D14")),
        CheckoutVariant("Промах T20", listOf("S20", "T17", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T17", "T20", "D5")),
        CheckoutVariant("Промах T17", listOf("S17", "T18", "BULL"))
    ),
    122 to listOf(
        CheckoutVariant("Основной", listOf("T18", "T18", "D7")),
        CheckoutVariant("Промах T18", listOf("S18", "T18", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T20", "T18", "D4")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "S10", "D16")),
        CheckoutVariant("Запасной", listOf("S18", "T18", "BULL"))
    ),
    123 to listOf(
        CheckoutVariant("Основной", listOf("T19", "T16", "D9")),
        CheckoutVariant("Промах T19", listOf("S19", "T18", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T20", "T13", "D12"))
    ),
    124 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T16", "D8")),
        CheckoutVariant("Промах T20", listOf("S20", "T18", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T20", "T20", "D2")),
        CheckoutVariant("Промах T20", listOf("S20", "T18", "BULL"))
    ),
    125 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T19", "D4")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "S13", "D16")),
        CheckoutVariant("Альтернативный", listOf("BULL", "T17", "D12")),
        CheckoutVariant("Запасной", listOf("25", "T20", "D20"))
    ),
    126 to listOf(
        CheckoutVariant("Основной", listOf("T19", "T19", "D6")),
        CheckoutVariant("Промах T19", listOf("S19", "T19", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T20", "T16", "D9"))
    ),
    127 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T17", "D8")),
        CheckoutVariant("Промах T20", listOf("S20", "T19", "BULL"))
    ),
    128 to listOf(
        CheckoutVariant("Основной", listOf("T18", "T18", "D10")),
        CheckoutVariant("Промах T18", listOf("S18", "T20", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T20", "T20", "D4"))
    ),
    129 to listOf(
        CheckoutVariant("Основной", listOf("T19", "T16", "D12")),
        CheckoutVariant("Промах T19", listOf("S19", "T20", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T19", "T20", "D6")),
        CheckoutVariant("Промах T19", listOf("S19", "T20", "BULL"))
    ),
    130 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T20", "D5")),
        CheckoutVariant("Промах T20", listOf("S20", "T20", "BULL")),
        CheckoutVariant("Альтернативный", listOf("T20", "T18", "D8")),
        CheckoutVariant("Запасной", listOf("T19", "T19", "D8"))
    ),
    131 to listOf(CheckoutVariant("Основной", listOf("T20", "T13", "D16"))),
    132 to listOf(
        CheckoutVariant("Основной", listOf("BULL", "BULL", "D16")),
        CheckoutVariant("Альтернативный", listOf("T20", "T16", "D12"))
    ),
    133 to listOf(CheckoutVariant("Основной", listOf("T20", "T19", "D8"))),
    134 to listOf(CheckoutVariant("Основной", listOf("T20", "T14", "D16"))),
    135 to listOf(
        CheckoutVariant("Основной", listOf("BULL", "T15", "D20")),
        CheckoutVariant("Альтернативный", listOf("T20", "T17", "D12"))
    ),
    136 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "D8"))),
    137 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T19", "D10")),
        CheckoutVariant("Альтернативный", listOf("T19", "T18", "D13"))
    ),
    138 to listOf(CheckoutVariant("Основной", listOf("T20", "T18", "D12"))),
    139 to listOf(CheckoutVariant("Основной", listOf("T20", "T13", "D20"))),
    140 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "D10"))),
    141 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T19", "D12")),
        CheckoutVariant("Альтернативный", listOf("T19", "T18", "D15"))
    ),
    142 to listOf(CheckoutVariant("Основной", listOf("T20", "T14", "D20"))),
    143 to listOf(CheckoutVariant("Основной", listOf("T20", "T17", "D16"))),
    144 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "D12"))),
    145 to listOf(CheckoutVariant("Основной", listOf("T20", "T15", "D20"))),
    146 to listOf(CheckoutVariant("Основной", listOf("T20", "T18", "D16"))),
    147 to listOf(CheckoutVariant("Основной", listOf("T20", "T17", "D18"))),
    148 to listOf(CheckoutVariant("Основной", listOf("T20", "T16", "D20"))),
    149 to listOf(CheckoutVariant("Основной", listOf("T20", "T19", "D16"))),
    150 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T20", "D15")),
        CheckoutVariant("Альтернативный", listOf("T20", "T18", "D18"))
    ),
    151 to listOf(CheckoutVariant("Основной", listOf("T20", "T17", "D20"))),
    152 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "D16"))),
    153 to listOf(CheckoutVariant("Основной", listOf("T20", "T19", "D18"))),
    154 to listOf(CheckoutVariant("Основной", listOf("T20", "T18", "D20"))),
    155 to listOf(CheckoutVariant("Основной", listOf("T20", "T19", "D19"))),
    156 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "D18"))),
    157 to listOf(
        CheckoutVariant("Основной", listOf("T20", "T19", "D20")),
        CheckoutVariant("Альтернативный", listOf("T19", "T20", "D20"))
    ),
    158 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "D19"))),
    160 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "D20"))),
    161 to listOf(CheckoutVariant("Основной", listOf("T20", "T17", "BULL"))),
    164 to listOf(CheckoutVariant("Основной", listOf("T20", "T18", "BULL"))),
    167 to listOf(CheckoutVariant("Основной", listOf("T20", "T19", "BULL"))),
    170 to listOf(CheckoutVariant("Основной", listOf("T20", "T20", "BULL")))
)

fun fmtNumber(v: Double): String =
    if (v == v.toLong().toDouble()) v.toLong().toString() else v.toString()

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
        screen == "number" -> NumberScreen(
            number = selectedNumber ?: 0,
            onBack = { screen = "range" }
        )
        screen == "settings" -> SettingsScreen(onBack = { screen = "main" })
        screen == "calc" -> CalculatorScreen(onBack = { screen = "main" })
    }
}

@Composable
fun MainMenuScreen(
    onRangeClick: (IntRange) -> Unit,
    onSettings: () -> Unit,
    onCalculator: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Darts Checkout",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = GoldAccent,
            letterSpacing = 3.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RangeButton(60, 99, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(60..99) }
            RangeButton(100, 134, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(100..134) }
        }
        Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            RangeButton(135, 170, Modifier.weight(1f).fillMaxHeight()) { onRangeClick(135..170) }
            Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SmallButton("Калькулятор", Modifier.fillMaxWidth().weight(1f), onCalculator)
                SmallButton("Настройки\nи инструкция", Modifier.fillMaxWidth().weight(1f), onSettings)
            }
        }
    }
}

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
fun RangeScreen(range: IntRange, onNumberClick: (Int) -> Unit, onBack: () -> Unit) {
    val numbers = remember(range) { range.toList() }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 16.dp, vertical = 10.dp)
            ) { Text("← Назад", color = Accent, fontSize = 15.sp) }
            Spacer(Modifier.width(12.dp))
            Text("${range.first}–${range.last}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
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

@Composable
fun NumberScreen(number: Int, onBack: () -> Unit) {
    val variants = CHECKOUTS[number].orEmpty()
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
        if (variants.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Вариантов нет", color = ImpossibleText, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text("Число $number невозможно закрыть за 3 дротика", color = Accent, fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 32.dp))
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                variants.forEach { v -> CheckoutCard(v) }
            }
        }
    }
}

@Composable
fun CheckoutCard(variant: CheckoutVariant) {
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
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
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
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CalculatorScreen(onBack: () -> Unit) {
    var display by remember { mutableStateOf("0") }
    var sequence by remember { mutableStateOf<List<String>>(emptyList()) }
    var accumulator by remember { mutableStateOf<Double?>(null) }
    var pendingOp by remember { mutableStateOf<String?>(null) }
    var replaceOnNext by remember { mutableStateOf(true) }

    fun compute() {
        val acc = accumulator ?: return
        val cur = display.toDoubleOrNull() ?: 0.0
        val res = when (pendingOp) {
            "+" -> acc + cur
            "−" -> acc - cur
            "×" -> acc * cur
            "÷" -> if (cur != 0.0) acc / cur else 0.0
            else -> cur
        }
        display = fmtNumber(res)
        accumulator = null
        pendingOp = null
        replaceOnNext = true
    }
    fun onDigit(d: String) {
        sequence = emptyList()
        if (replaceOnNext) { display = d; replaceOnNext = false }
        else display = if (display == "0") d else display + d
    }
    fun onDot() {
        sequence = emptyList()
        if (replaceOnNext) { display = "0."; replaceOnNext = false }
        else if (!display.contains(".")) display += "."
    }
    fun onDart(dart: Dart) {
        val cur = display.toDoubleOrNull() ?: 0.0
        display = fmtNumber(cur + dart.score)
        sequence = sequence + dart.toString()
        replaceOnNext = true
    }
    fun onOp(op: String) {
        sequence = emptyList()
        if (pendingOp != null && !replaceOnNext) compute()
        accumulator = display.toDoubleOrNull() ?: 0.0
        pendingOp = op
        replaceOnNext = true
    }
    fun onEq() { sequence = emptyList(); if (pendingOp != null) compute() }
    fun onClear() { display = "0"; sequence = emptyList(); accumulator = null; pendingOp = null; replaceOnNext = true }
    fun onBackspace() {
        sequence = emptyList()
        if (replaceOnNext) display = "0"
        else display = if (display.length > 1) display.dropLast(1) else "0"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(TileBgDark)
                    .clickable { onBack() }.padding(horizontal = 14.dp, vertical = 8.dp)
            ) { Text("← Назад", color = Accent, fontSize = 14.sp) }
            Spacer(Modifier.width(10.dp))
            Text("Калькулятор", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(64.dp)
                .clip(RoundedCornerShape(14.dp)).background(TileBgDark)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sequence.joinToString("+"),
                color = Accent, fontSize = 14.sp, fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f), maxLines = 3, softWrap = true
            )
            Text(
                text = display,
                color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold,
                maxLines = 1, textAlign = TextAlign.End
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().weight(1.6f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            for (n in 20 downTo 10) {
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    DartCell(Dart(n, 3), Modifier.weight(1f)) { onDart(it) }
                    DartCell(Dart(n, 1), Modifier.weight(1f)) { onDart(it) }
                    DartCell(Dart(n, 2), Modifier.weight(1f)) { onDart(it) }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("C", Modifier.weight(1f)) { onClear() }
                CalcBtn("⌫", Modifier.weight(1f)) { onBackspace() }
                CalcBtn("%", Modifier.weight(1f)) {
                    sequence = emptyList()
                    display = fmtNumber((display.toDoubleOrNull() ?: 0.0) / 100.0)
                    replaceOnNext = true
                }
                CalcBtn("÷", Modifier.weight(1f), OpColor) { onOp("÷") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("7", Modifier.weight(1f)) { onDigit("7") }
                CalcBtn("8", Modifier.weight(1f)) { onDigit("8") }
                CalcBtn("9", Modifier.weight(1f)) { onDigit("9") }
                CalcBtn("×", Modifier.weight(1f), OpColor) { onOp("×") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("4", Modifier.weight(1f)) { onDigit("4") }
                CalcBtn("5", Modifier.weight(1f)) { onDigit("5") }
                CalcBtn("6", Modifier.weight(1f)) { onDigit("6") }
                CalcBtn("−", Modifier.weight(1f), OpColor) { onOp("−") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("1", Modifier.weight(1f)) { onDigit("1") }
                CalcBtn("2", Modifier.weight(1f)) { onDigit("2") }
                CalcBtn("3", Modifier.weight(1f)) { onDigit("3") }
                CalcBtn("+", Modifier.weight(1f), OpColor) { onOp("+") }
            }
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                CalcBtn("0", Modifier.weight(2f)) { onDigit("0") }
                CalcBtn(".", Modifier.weight(1f)) { onDot() }
                CalcBtn("=", Modifier.weight(1f), OpColor) { onEq() }
            }
        }
    }
}

@Composable
fun DartCell(dart: Dart, modifier: Modifier, onClick: (Dart) -> Unit) {
    Box(
        modifier = modifier.fillMaxHeight()
            .clip(RoundedCornerShape(8.dp)).background(TileBg)
            .clickable { onClick(dart) },
        contentAlignment = Alignment.Center
    ) {
        Text(dart.toString(), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun CalcBtn(label: String, modifier: Modifier, color: Color = TileBgDark, onClick: () -> Unit) {
    Box(
        modifier = modifier.fillMaxHeight()
            .clip(RoundedCornerShape(12.dp)).background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    }
}

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

@Composable
fun SmallButton(label: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(18.dp)).background(TileBgDark).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Accent, textAlign = TextAlign.Center)
    }
}

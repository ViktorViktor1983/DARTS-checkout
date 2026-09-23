package com.example.dartscheckout.data

val DEFAULT_CHECKOUTS: Map<Int, List<CheckoutVariant>> = mapOf(
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
        CheckoutVariant("Альтернативный", listOf("T20", "T20", "D2"))
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
        CheckoutVariant("Альтернативный", listOf("T19", "T20", "D6"))
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

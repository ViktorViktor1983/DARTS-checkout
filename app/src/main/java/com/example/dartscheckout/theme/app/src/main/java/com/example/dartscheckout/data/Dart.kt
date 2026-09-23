package com.example.dartscheckout.data

data class Dart(val sector: Int, val multiplier: Int) {
    val score: Int get() = sector * multiplier

    override fun toString(): String = when {
        sector == 25 && multiplier == 2 -> "BULL"
        sector == 25 -> "25"
        multiplier == 1 -> "S$sector"
        multiplier == 2 -> "D$sector"
        else -> "T$sector"
    }

    companion object {
        val ALL: List<Dart> = buildList {
            for (s in 1..20) for (m in 1..3) add(Dart(s, m))
            add(Dart(25, 1))
            add(Dart(25, 2))
        }
    }
}

fun parseDart(s: String): Dart? {
    if (s == "BULL") return Dart(25, 2)
    if (s == "25") return Dart(25, 1)
    if (s.isEmpty()) return null
    val mult = when (s[0]) {
        'S' -> 1
        'D' -> 2
        'T' -> 3
        else -> return null
    }
    val num = s.substring(1).toIntOrNull() ?: return null
    return Dart(num, mult)
}

fun sumOfDarts(throws: List<String>): Int =
    throws.mapNotNull(::parseDart).sumOf { it.score }

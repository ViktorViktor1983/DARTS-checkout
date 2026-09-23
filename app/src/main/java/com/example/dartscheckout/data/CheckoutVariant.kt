package com.example.dartscheckout.data

data class CheckoutVariant(
    val label: String,
    val throws: List<String>
)

fun fmtNumber(v: Double): String =
    if (v == v.toLong().toDouble()) v.toLong().toString() else v.toString()

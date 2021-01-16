package com.bailram.aplikasikasir.utils

import java.text.NumberFormat
import java.util.*

object CurrencyFormat {
    fun convertCurrency(number: String) : String {
        val format = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("IDR")

        return format.format(number.toInt())
    }
}
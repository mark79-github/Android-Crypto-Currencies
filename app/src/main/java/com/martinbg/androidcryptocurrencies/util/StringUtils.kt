package com.martinbg.androidcryptocurrencies.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

object StringUtils {
    fun String.capitalizeFirstChar(): String {
        return this.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }

    fun formatNumberToCurrency(
        value: String,
        minFractionDigits: Int,
        maxFractionDigits: Int,
        scale: Int,
        locale: Locale
    ): String {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance(locale)
        formatter.minimumFractionDigits = minFractionDigits
        formatter.maximumFractionDigits = maxFractionDigits
        return formatter.format(
            BigDecimal(value).setScale(
                scale,
                RoundingMode.HALF_EVEN
            )
        )
    }
}
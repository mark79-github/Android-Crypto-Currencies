package com.martinbg.androidcryptocurrencies.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "crypto_currency")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "current_price")
    var currentPrice: BigDecimal,
    @ColumnInfo(name = "high_24h")
    var high24h: BigDecimal,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "low_24h")
    var low24h: BigDecimal,
    @ColumnInfo(name = "market_cap")
    var marketCap: Long,
    @ColumnInfo(name = "market_cap_change_percentage_24h")
    var marketCapChangePercentage24h: Double,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "price_change_percentage_24h")
    var priceChangePercentage24h: Double,
    @ColumnInfo(name = "symbol")
    var symbol: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
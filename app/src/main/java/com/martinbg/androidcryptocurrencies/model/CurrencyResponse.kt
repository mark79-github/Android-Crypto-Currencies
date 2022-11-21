package com.martinbg.androidcryptocurrencies.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("current_price")
    val currentPrice: Double,
    @SerializedName("high_24h")
    val high24h: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("low_24h")
    val low24h: Double,
    @SerializedName("market_cap")
    val marketCap: Long,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("symbol")
    val symbol: String
)
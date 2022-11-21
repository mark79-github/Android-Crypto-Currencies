package com.martinbg.androidcryptocurrencies.model

import com.google.gson.annotations.SerializedName

data class CurrencyDetailResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("market_data")
    val marketData: MarketData,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
)

data class Image(
    @SerializedName("large")
    val large: String,
    @SerializedName("small")
    val small: String,
)

data class MarketData(
    @SerializedName("current_price")
    val currentPrice: CurrentPrice,
    @SerializedName("high_24h")
    val high24h: High24h,
    @SerializedName("low_24h")
    val low24h: Low24h,
    @SerializedName("market_cap")
    val marketCap: MarketCap,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,
    @SerializedName("market_cap_change_percentage_24h_in_currency")
    val marketCapChangePercentage24hInCurrency: MarketCapChangePercentage24hInCurrency,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency,
)

data class CurrentPrice(
    @SerializedName("usd")
    val usd: Double,
)

data class High24h(
    @SerializedName("usd")
    val usd: Double,
)

data class Low24h(
    @SerializedName("usd")
    val usd: Double,
)

data class MarketCap(
    @SerializedName("usd")
    val usd: Long,
)

data class MarketCapChangePercentage24hInCurrency(
    @SerializedName("usd")
    val usd: Double,
)

data class PriceChangePercentage24hInCurrency(
    @SerializedName("usd")
    val usd: Double,
)
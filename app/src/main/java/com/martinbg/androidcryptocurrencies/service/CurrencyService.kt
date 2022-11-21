package com.martinbg.androidcryptocurrencies.service

import com.martinbg.androidcryptocurrencies.model.CurrencyDetailResponse
import com.martinbg.androidcryptocurrencies.model.CurrencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyService {

    @GET("coins/markets")
    fun getCurrencies(@Query("vs_currency") currency: String? = "usd"): Call<List<CurrencyResponse>>

    @GET("coins/{id}")
    fun getCurrencyById(@Path("id") name: String): Call<CurrencyDetailResponse>
}
package com.martinbg.androidcryptocurrencies.repository

import android.content.Context
import com.martinbg.androidcryptocurrencies.db.dao.CurrencyDao
import com.martinbg.androidcryptocurrencies.db.entity.CurrencyEntity
import com.martinbg.androidcryptocurrencies.db.entity.FavouriteEntity
import com.martinbg.androidcryptocurrencies.db.relation.CurrencyAndFavourite
import com.martinbg.androidcryptocurrencies.model.CurrencyDetailResponse
import com.martinbg.androidcryptocurrencies.model.CurrencyResponse
import com.martinbg.androidcryptocurrencies.service.CurrencyService
import com.martinbg.androidcryptocurrencies.util.NetworkUtil
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyRepository constructor(
    private val context: Context,
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao
) {
    suspend fun getCurrencies(): List<CurrencyAndFavourite> {
        return try {
            if (NetworkUtil.isConnected(context)) {
                val currencyList = currencyService.getCurrencies().execute().body()
                val roomCurrencies = currencyList?.map { mapResponseToCurrencyDbModel(it) }
                val roomFavourites = currencyList?.map { mapResponseToFavouriteDbModel(it) }
                roomCurrencies?.let { currencyDao.insertCurrencies(it) }
                roomFavourites?.let { currencyDao.insertFavourites(it) }
            }
            currencyDao.getCurrencies()
        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf()
        }
    }

    suspend fun getCurrencyById(id: String): CurrencyAndFavourite? {
        return try {
            if (NetworkUtil.isConnected(context)) {
                val currency = currencyService.getCurrencyById(id).execute().body()
                val roomCurrency = currency?.let { mapResponseDetailsToCurrencyDbModel(it) }
                roomCurrency?.let { currencyDao.updateCurrency(it) }
            }
            return currencyDao.getCurrencyById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateCurrency(currency: CurrencyEntity) {
        currencyDao.updateCurrency(currency)
    }

    suspend fun updateFavourite(favourite: FavouriteEntity) {
        currencyDao.updateFavourite(favourite)
    }

    private fun mapResponseToCurrencyDbModel(response: CurrencyResponse): CurrencyEntity {
        return CurrencyEntity(
            id = response.id,
            name = response.name,
            image = response.image,
            symbol = response.symbol,
            currentPrice = BigDecimal(response.currentPrice.toString()).setScale(
                6,
                RoundingMode.HALF_EVEN
            ),
            marketCap = response.marketCap,
            high24h = BigDecimal(response.high24h.toString()).setScale(6, RoundingMode.HALF_EVEN),
            low24h = BigDecimal(response.low24h.toString()).setScale(6, RoundingMode.HALF_EVEN),
            priceChangePercentage24h = response.priceChangePercentage24h,
            marketCapChangePercentage24h = response.marketCapChangePercentage24h,
        )
    }

    private fun mapResponseDetailsToCurrencyDbModel(response: CurrencyDetailResponse): CurrencyEntity {
        return CurrencyEntity(
            id = response.id,
            name = response.name,
            image = response.image.large,
            symbol = response.symbol,
            currentPrice = BigDecimal(response.marketData.currentPrice.usd.toString()).setScale(
                6,
                RoundingMode.HALF_EVEN
            ),
            marketCap = response.marketData.marketCap.usd,
            high24h = BigDecimal(response.marketData.high24h.usd.toString()).setScale(
                6,
                RoundingMode.HALF_EVEN
            ),
            low24h = BigDecimal(response.marketData.low24h.usd.toString()).setScale(
                6,
                RoundingMode.HALF_EVEN
            ),
            priceChangePercentage24h = response.marketData.priceChangePercentage24hInCurrency.usd,
            marketCapChangePercentage24h = response.marketData.marketCapChangePercentage24hInCurrency.usd,
        )
    }

    private fun mapResponseToFavouriteDbModel(response: CurrencyResponse): FavouriteEntity {
        return FavouriteEntity(
            currencyId = response.id
        )
    }
}
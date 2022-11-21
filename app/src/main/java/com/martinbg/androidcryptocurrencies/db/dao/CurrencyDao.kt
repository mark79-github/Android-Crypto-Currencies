package com.martinbg.androidcryptocurrencies.db.dao

import androidx.room.*
import com.martinbg.androidcryptocurrencies.db.entity.CurrencyEntity
import com.martinbg.androidcryptocurrencies.db.entity.FavouriteEntity
import com.martinbg.androidcryptocurrencies.db.relation.CurrencyAndFavourite

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateCurrency(currencyEntity: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourites(favourites: List<FavouriteEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favouriteEntity: FavouriteEntity)

    @Transaction
    @Query("SELECT * FROM crypto_currency WHERE id = :id")
    suspend fun getCurrencyById(id: String): CurrencyAndFavourite

    @Transaction
    @Query("SELECT * FROM crypto_currency")
    suspend fun getCurrencies(): List<CurrencyAndFavourite>
}
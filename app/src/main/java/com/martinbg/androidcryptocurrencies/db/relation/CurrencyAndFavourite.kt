package com.martinbg.androidcryptocurrencies.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.martinbg.androidcryptocurrencies.db.entity.CurrencyEntity
import com.martinbg.androidcryptocurrencies.db.entity.FavouriteEntity

data class CurrencyAndFavourite(
    @Embedded var currency: CurrencyEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "currencyId"
    )
    var favourite: FavouriteEntity
)


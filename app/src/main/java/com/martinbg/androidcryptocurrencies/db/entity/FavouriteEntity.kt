package com.martinbg.androidcryptocurrencies.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class FavouriteEntity(
    @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    val currencyId: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FavouriteEntity

        if (currencyId != other.currencyId) return false

        return true
    }

    override fun hashCode(): Int {
        return currencyId.hashCode()
    }
}

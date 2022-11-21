package com.martinbg.androidcryptocurrencies.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.martinbg.androidcryptocurrencies.db.converter.BigDecimalDoubleTypeConverter
import com.martinbg.androidcryptocurrencies.db.dao.CurrencyDao
import com.martinbg.androidcryptocurrencies.db.entity.CurrencyEntity
import com.martinbg.androidcryptocurrencies.db.entity.FavouriteEntity

@Database(entities = [CurrencyEntity::class, FavouriteEntity::class], version = 1)
@TypeConverters(BigDecimalDoubleTypeConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null

        fun getDatabase(context: Context): CurrencyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "cryptocurrency.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
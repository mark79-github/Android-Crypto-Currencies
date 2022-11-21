package com.martinbg.androidcryptocurrencies.viewmodel

import androidx.lifecycle.ViewModel
import com.martinbg.androidcryptocurrencies.db.entity.CurrencyEntity
import com.martinbg.androidcryptocurrencies.db.entity.FavouriteEntity
import com.martinbg.androidcryptocurrencies.db.relation.CurrencyAndFavourite
import com.martinbg.androidcryptocurrencies.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val currencyListStateFlow = MutableStateFlow<List<CurrencyAndFavourite>>(arrayListOf())

    private val selectedCurrencyStateFlow = MutableStateFlow<CurrencyAndFavourite?>(null)

    val currencyList: StateFlow<List<CurrencyAndFavourite>> = currencyListStateFlow.asStateFlow()

    val selectedCurrency: StateFlow<CurrencyAndFavourite?> = selectedCurrencyStateFlow.asStateFlow()

    fun getCurrencyList() = runBlocking {
        val currencyList = currencyRepository.getCurrencies()
        currencyListStateFlow.value = currencyList
    }

    fun getCurrencyById(name: String) = runBlocking {
        val currency = currencyRepository.getCurrencyById(name)
        selectedCurrencyStateFlow.value = currency ?: return@runBlocking
    }

    fun updateCurrency(currency: CurrencyEntity, favourite: FavouriteEntity) = runBlocking {
        currencyRepository.updateCurrency(currency)
        currencyRepository.updateFavourite(favourite)
        selectedCurrencyStateFlow.value =
            selectedCurrencyStateFlow.value?.copy(favourite = favourite)
    }
}
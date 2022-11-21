package com.martinbg.androidcryptocurrencies.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martinbg.androidcryptocurrencies.repository.CurrencyRepository
import com.martinbg.androidcryptocurrencies.viewmodel.CurrencyViewModel

class CurrencyViewModelFactory(
    private val currencyRepository: CurrencyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(currencyRepository) as T
    }
}
package com.martinbg.androidcryptocurrencies.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.martinbg.androidcryptocurrencies.R
import com.martinbg.androidcryptocurrencies.adapter.CurrencyAdapter
import com.martinbg.androidcryptocurrencies.databinding.ActivityMainBinding
import com.martinbg.androidcryptocurrencies.db.database.CurrencyDatabase
import com.martinbg.androidcryptocurrencies.factory.CurrencyViewModelFactory
import com.martinbg.androidcryptocurrencies.repository.CurrencyRepository
import com.martinbg.androidcryptocurrencies.service.CurrencyService
import com.martinbg.androidcryptocurrencies.util.NetworkUtil
import com.martinbg.androidcryptocurrencies.util.RetrofitHelper
import com.martinbg.androidcryptocurrencies.viewmodel.CurrencyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var currencyService: CurrencyService

    private lateinit var currencyRepository: CurrencyRepository

    lateinit var currencyViewModel: CurrencyViewModel
    lateinit var adapter: CurrencyAdapter

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init()
        observeData()

        if (!NetworkUtil.isConnected(this)) {
            Snackbar.make(
                binding.root,
                "No internet connection, information could be outdated",
                Snackbar.LENGTH_LONG
            ).show()
        }

        GlobalScope.launch {
            currencyViewModel.getCurrencyList()
        }
    }

    fun init() {
        val currencyDao = CurrencyDatabase.getDatabase(applicationContext).currencyDao()
        currencyService = RetrofitHelper.getClient().create(CurrencyService::class.java)
        currencyRepository = CurrencyRepository(this, currencyService, currencyDao)
        val currencyViewModelFactory = CurrencyViewModelFactory(currencyRepository)
        currencyViewModel =
            ViewModelProvider(this, currencyViewModelFactory)[CurrencyViewModel::class.java]
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun observeData() {
        GlobalScope.launch {
            currencyViewModel.currencyList.collect {
                runOnUiThread {
                    val currencies = it
                    val sortedCurrencies = currencies.sortedWith(
                        compareBy(
                            { !it.favourite.isFavourite },
                            { it.currency.marketCap },
                        )
                    )
                    adapter = CurrencyAdapter(sortedCurrencies)
                    binding.currenciesList.adapter = adapter
                    binding.tvCurrenciesCount.text = getString(R.string.currencies_size, it.size)
                }
            }
        }
    }
}
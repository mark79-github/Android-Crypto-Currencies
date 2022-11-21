package com.martinbg.androidcryptocurrencies.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.martinbg.androidcryptocurrencies.R
import com.martinbg.androidcryptocurrencies.activity.MainActivity
import com.martinbg.androidcryptocurrencies.databinding.FragmentCurrencyDetailsBinding
import com.martinbg.androidcryptocurrencies.util.StringUtils.capitalizeFirstChar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding
    private val btnLikeInitialState: Boolean = true
    private var btnLikeCurrentState: Boolean = true
    private var currentPosition: Int = 0

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currencyId = arguments?.getString("currency_id", null)
        currentPosition = arguments?.getInt("current_position") ?: 0
        GlobalScope.launch {
            (activity as? MainActivity)?.currencyViewModel?.getCurrencyById(
                currencyId ?: return@launch
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            (activity as? MainActivity)?.currencyViewModel?.selectedCurrency?.collect {
                val model =
                    it?.copy(currency = it.currency.copy(name = it.currency.name.capitalizeFirstChar()))
                        ?: return@collect
                binding.model = model
                (activity as? MainActivity)?.runOnUiThread {
                    setDataBinding()
                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it.currency.image)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivFlag)
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CurrencyDetailsFragmentTag", "onDetach()")
        if (btnLikeInitialState != btnLikeCurrentState) {
            (activity as MainActivity).adapter.notifyItemChanged(currentPosition)
        }
    }

    private fun setDataBinding() {
        binding.model ?: return

        if (binding.model?.favourite?.isFavourite == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        if (binding.model?.currency?.priceChangePercentage24h!! < 0) {
            setTextViewColor(binding.tvPercentagePrice, R.color.red)
        } else {
            setTextViewColor(binding.tvPercentagePrice, R.color.green)
        }

        if (binding.model?.currency?.marketCapChangePercentage24h!! < 0) {
            setTextViewColor(binding.tvPercentageMarketCap, R.color.red)
        } else {
            setTextViewColor(binding.tvPercentageMarketCap, R.color.green)
        }

        binding.btnLike.setOnClickListener {
            val currency = binding.model
            currency?.favourite?.isFavourite = currency?.favourite?.isFavourite != true

            if (currency?.favourite?.isFavourite == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            btnLikeCurrentState = !btnLikeCurrentState

            lifecycleScope.launch {
                (activity as? MainActivity)?.currencyViewModel?.updateCurrency(
                    currency?.currency ?: return@launch, currency.favourite
                )
            }
        }
    }

    private fun setTextViewColor(textView: TextView, color: Int) {
        activity?.resources?.let {
            textView.setTextColor(
                it.getColor(
                    color,
                    activity?.theme
                )
            )
        }
    }
}
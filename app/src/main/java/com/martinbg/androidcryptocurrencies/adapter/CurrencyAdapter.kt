package com.martinbg.androidcryptocurrencies.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martinbg.androidcryptocurrencies.R
import com.martinbg.androidcryptocurrencies.activity.MainActivity
import com.martinbg.androidcryptocurrencies.databinding.CurrencyListItemBinding
import com.martinbg.androidcryptocurrencies.db.relation.CurrencyAndFavourite
import com.martinbg.androidcryptocurrencies.fragment.CurrencyDetailsFragment
import com.martinbg.androidcryptocurrencies.util.StringUtils
import com.martinbg.androidcryptocurrencies.util.StringUtils.capitalizeFirstChar
import java.util.*


class CurrencyAdapter(currencies: List<CurrencyAndFavourite>) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    private lateinit var binding: CurrencyListItemBinding
    private lateinit var context: Context
    private var currencyList = currencies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CurrencyListItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencyList[position], position)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CurrencyAndFavourite, position: Int) {
            binding.apply {
                name = item.currency.name.capitalizeFirstChar()
                price = StringUtils.formatNumberToCurrency(
                    item.currency.currentPrice.toString(),
                    2,
                    6,
                    6,
                    Locale.US
                )
                symbol = item.currency.symbol
                ivLiked.visibility = if (item.favourite.isFavourite) View.VISIBLE else View.GONE

                Glide
                    .with(root.context)
                    .load(item.currency.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivFlag)

                root.setOnClickListener {
                    (it.context as MainActivity).supportFragmentManager.commit {
                        val bundle = Bundle()
                        bundle.putString("currency_id", item.currency.id)
                        bundle.putInt("current_position", position)
                        replace(R.id.container_view, CurrencyDetailsFragment::class.java, bundle)
                        addToBackStack("currency_list")
                    }
                }
            }
        }
    }
}
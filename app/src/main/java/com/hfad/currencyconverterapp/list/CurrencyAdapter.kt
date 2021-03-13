package com.hfad.currencyconverterapplication.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.currencyconverterapp.databinding.ItemListBinding
import com.hfad.currencyconverterapp.data.model.Currency

/**
 * Currency adapter
 *
 * this adapter bind model data currency to RecyclerView
 *
 * @property itemOnClick get lambda expression
 * @constructor Create empty Currency adapter
 */
class CurrencyAdapter(private val itemOnClick: (Currency) -> Unit) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var currencyList = emptyList<Currency>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context) // get inflate root view from parent (recyclerView)
        // get item view
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding.root) // return item list view
    }

    /**
     * On bind view holder
     *
     * this fun bind holder with data model
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList.get(position)
        holder.binding?.valute = currency
        holder.binding?.root?.setOnClickListener { itemOnClick(currency) }
    }

    override fun getItemCount(): Int = currencyList.size

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // binding item view with field`s item list view
        val binding: ItemListBinding? = DataBindingUtil.bind(itemView)
    }
}
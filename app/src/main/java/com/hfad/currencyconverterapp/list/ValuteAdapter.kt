package com.hfad.currencyconverterapplication.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.currencyconverterapp.databinding.ItemListBinding
import com.hfad.currencyconverterapp.model.Valute


class ValuteAdapter(private val itemOnClick: (Valute) -> Unit) :
    RecyclerView.Adapter<ValuteAdapter.ValuteViewHolder>() {

    var valute = emptyList<Valute>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return ValuteViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: ValuteViewHolder, position: Int) {
        val valute = valute.get(position)
        holder.binding?.valute = valute
        holder.binding?.root?.setOnClickListener { itemOnClick(valute) }
    }

    override fun getItemCount(): Int = valute.size

    class ValuteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemListBinding? = DataBindingUtil.bind(itemView)
    }
}
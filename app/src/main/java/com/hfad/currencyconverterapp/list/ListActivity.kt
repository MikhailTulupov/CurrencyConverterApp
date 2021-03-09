package com.hfad.currencyconverterapp.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.currencyconverterapp.R
import com.hfad.currencyconverterapp.databinding.ActivityListBinding
import com.hfad.currencyconverterapp.model.Currency
import com.hfad.currencyconverterapp.model.Valute
import com.hfad.currencyconverterapp.model.ValuteRepository
import com.hfad.currencyconverterapplication.list.ValuteAdapter
import java.lang.NumberFormatException
import java.text.DecimalFormat

class ListActivity : AppCompatActivity() {

    private lateinit var valuteRepository: ValuteRepository
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: ValuteAdapter
    private lateinit var lastetOnClickValute: Valute

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        valuteRepository = ValuteRepository()


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.valueRusValuteText.isEnabled = false
        binding.changeValue.isEnabled = false

        adapter = ValuteAdapter {
            binding.changeValuteName.text = it.Name
            val rubValue = binding.valueRusValuteText.text.toString().toDouble()
            val currency = Currency(rubValue, it.Value, it.Nominal).currency()
            binding.changeValue.text = SpannableStringBuilder(DecimalFormat("#.##").format(currency))
            lastetOnClickValute = it
            binding.valueRusValuteText.isEnabled = true
        }

        adapter.valute = valuteRepository.getValuteRepository()

        binding.recyclerView.adapter = adapter



        binding.valueRusValuteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                try {
                    val rubValue = binding.valueRusValuteText.text.toString().toDouble()
                    val otherValue = lastetOnClickValute.Value
                    val nominal = lastetOnClickValute.Nominal
                    val currency = Currency(rubValue, otherValue, nominal).currency()
                    binding.changeValue.text = SpannableStringBuilder(DecimalFormat("#.##").format(currency))
                } catch (exc: NumberFormatException) {
                    binding.valueRusValuteText.text = SpannableStringBuilder("0")
                }

            }

        }
        )
    }



}
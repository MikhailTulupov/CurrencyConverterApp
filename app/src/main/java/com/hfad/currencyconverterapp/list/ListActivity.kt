package com.hfad.currencyconverterapp.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.currencyconverterapp.R
import com.hfad.currencyconverterapp.data.dataBase.FillDBTask
import com.hfad.currencyconverterapp.databinding.ActivityListBinding
import com.hfad.currencyconverterapp.data.Converter
import com.hfad.currencyconverterapp.data.model.Currency
import com.hfad.currencyconverterapp.data.model.CurrencyRepository
import com.hfad.currencyconverterapplication.list.CurrencyAdapter
import java.lang.NumberFormatException
import java.text.DecimalFormat

class ListActivity : AppCompatActivity() {

    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var binding: ActivityListBinding
    private lateinit var mAdapter: CurrencyAdapter
    private lateinit var mLastedOnClickCurrency: Currency

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("EXTRA_CURRENCY_ID", mLastedOnClickCurrency.ID)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val id = savedInstanceState.getString("EXTRA_CURRENCY_ID")
        mLastedOnClickCurrency = id?.let { CurrencyRepository().getCurrency(it) }!!
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        currencyRepository = FillDBTask(this,"create").loadInBackground()

        setSupportActionBar(binding.toolbar)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.valueRusValuteText.isEnabled = false
        binding.changeValue.isEnabled = false

        mAdapter = CurrencyAdapter {
            binding.changeValuteName.text = it.Name
            val rubValue = binding.valueRusValuteText.text.toString().toDouble()
            val currency = Converter(rubValue, it.Value, it.Nominal).currency()
            binding.changeValue.text = SpannableStringBuilder(DecimalFormat("#.##").format(currency))
            mLastedOnClickCurrency = it
            binding.valueRusValuteText.isEnabled = true
        }

        mAdapter.valute = currencyRepository.getCurrencyRepository()

        binding.recyclerView.adapter = mAdapter



        binding.valueRusValuteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                try {
                    val rubValue = binding.valueRusValuteText.text.toString().toDouble()
                    val otherValue = mLastedOnClickCurrency.Value
                    val nominal = mLastedOnClickCurrency.Nominal
                    val currency = Converter(rubValue, otherValue, nominal).currency()
                    binding.changeValue.text = SpannableStringBuilder(DecimalFormat("#.##").format(currency))
                } catch (exc: NumberFormatException) {
                    binding.valueRusValuteText.text = SpannableStringBuilder("0")
                }

            }

        }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.currency_converter_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_update) {
            currencyRepository = FillDBTask(this,"update").loadInBackground()
        }
        return super.onOptionsItemSelected(item)
    }

}
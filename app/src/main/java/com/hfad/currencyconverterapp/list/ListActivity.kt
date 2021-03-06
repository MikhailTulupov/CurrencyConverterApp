package com.hfad.currencyconverterapp.list

import android.app.AlarmManager
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.currencyconverterapp.R
import com.hfad.currencyconverterapp.data.Converter
import com.hfad.currencyconverterapp.data.dataBase.FillDBTask
import com.hfad.currencyconverterapp.data.model.Currency
import com.hfad.currencyconverterapp.data.model.CurrencyRepository
import com.hfad.currencyconverterapp.databinding.ActivityListBinding
import com.hfad.currencyconverterapp.service.BootReceive
import com.hfad.currencyconverterapp.service.SettableAlarmManager
import com.hfad.currencyconverterapplication.list.CurrencyAdapter
import java.text.DecimalFormat
import kotlin.properties.Delegates

/**
 * List activity
 *
 * Main Activity in this application
 *
 * @constructor Create empty List activity
 */

class ListActivity : AppCompatActivity(), SettableAlarmManager {

    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: CurrencyAdapter
    private lateinit var lastedOnClickCurrency: Currency
    private lateinit var alarmManager: AlarmManager
    private lateinit var currencyID: String
    private var textIsEnabled by Delegates.notNull<Boolean>()

    companion object {
        private const val KEY_CURRENCY_ID = "KEY_CURRENCY_ID"
        private const val KEY_IS_ENABLED = "KEY_IS_ENABLED"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        try {
            outState.putString(KEY_CURRENCY_ID, currencyID)
            outState.putBoolean(KEY_IS_ENABLED, binding.valueRusCurrencyText.isEnabled)
        } catch (exc: UninitializedPropertyAccessException) {
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        currencyID = savedInstanceState.getString(KEY_CURRENCY_ID).toString()
        textIsEnabled = savedInstanceState.getBoolean(KEY_IS_ENABLED)
        super.onRestoreInstanceState(savedInstanceState)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get currency repos from DB
        currencyRepository = FillDBTask(this, "create").loadInBackground()

        // get root view
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        alarmManager = setAlarmManager(this)

        val receiver = ComponentName(this, BootReceive::class.java)

        this.packageManager.setComponentEnabledSetting(
            receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        adapter = CurrencyAdapter {
            binding.changeCurrencyName.text = it.Name

            val rubValue: Double = try {
                binding.valueRusCurrencyText.text.toString().toDouble()
            } catch (exc: NumberFormatException) {
                0.0
            }
//            val rubValue = binding.valueRusCurrencyText.text.toString().toDouble()

            val currency = Converter(rubValue, it.Value, it.Nominal).currency()

            binding.changeValue.text = SpannableStringBuilder(DecimalFormat("#.##").format(currency))

            currencyID = it.ID
            lastedOnClickCurrency = it
            binding.valueRusCurrencyText.isEnabled = true
            textIsEnabled = true
        }

        adapter.currencyList = currencyRepository.getCurrencyRepository()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.valueRusCurrencyText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_UP) {
                    try {
                        val rubValue: Double
//                        val rubValue = binding.valueRusCurrencyText.text.toString().toDouble()
                        var str = binding.valueRusCurrencyText.text.toString()
                        if (str.isEmpty()) {
                            str = "0"
                        }
                        rubValue = str.toDouble()
                        val otherValue = lastedOnClickCurrency.Value
                        val nominal = lastedOnClickCurrency.Nominal
                        val currency = Converter(rubValue, otherValue, nominal).currency()
                        binding.changeValue.text = SpannableStringBuilder(DecimalFormat("#.##").format(currency))
                        return true

                    } catch (exc: UninitializedPropertyAccessException) {
                        binding.valueRusCurrencyText.text = SpannableStringBuilder("0")
                    }

                }
                return false
            }

        })


    }

    override fun onStart() {
        super.onStart()
        currencyRepository = FillDBTask(baseContext, "getDB").loadInBackground()
        adapter.currencyList = currencyRepository.getCurrencyRepository()
    }

    override fun onRestart() {
        super.onRestart()
        currencyRepository = FillDBTask(baseContext, "getDB").loadInBackground()
        adapter.currencyList = currencyRepository.getCurrencyRepository()
    }


    override fun onResume() {
        super.onResume()
        try {
            lastedOnClickCurrency = currencyRepository.getCurrency(currencyID)!!
            binding.valueRusCurrencyText.isEnabled = textIsEnabled
            binding.changeCurrencyName.text = lastedOnClickCurrency.Name
        } catch (exc: Exception) {
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.currency_converter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_update) {
            currencyRepository = FillDBTask(baseContext, "update").loadInBackground()
            adapter.currencyList = currencyRepository.getCurrencyRepository()
        }
        return super.onOptionsItemSelected(item)
    }

}
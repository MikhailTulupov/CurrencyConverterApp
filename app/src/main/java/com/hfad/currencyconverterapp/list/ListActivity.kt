package com.hfad.currencyconverterapp.list

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.currencyconverterapp.service.AlarmService
import com.hfad.currencyconverterapp.service.BootReceive
import com.hfad.currencyconverterapp.R
import com.hfad.currencyconverterapp.data.dataBase.FillDBTask
import com.hfad.currencyconverterapp.databinding.ActivityListBinding
import com.hfad.currencyconverterapp.data.Converter
import com.hfad.currencyconverterapp.data.model.Currency
import com.hfad.currencyconverterapp.data.model.CurrencyRepository
import com.hfad.currencyconverterapplication.list.CurrencyAdapter
import java.lang.NumberFormatException
import java.text.DecimalFormat

/**
 * List activity
 *
 * Main Activity in this application
 *
 * @constructor Create empty List activity
 */

class ListActivity : AppCompatActivity() {

    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: CurrencyAdapter
    private lateinit var lastedOnClickCurrency: Currency
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("EXTRA_CURRENCY_ID", lastedOnClickCurrency.ID)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val id = savedInstanceState.getString("EXTRA_CURRENCY_ID")
        lastedOnClickCurrency = id?.let { CurrencyRepository().getCurrency(it) }!!
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get root view
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // get currency repos from DB
        currencyRepository = FillDBTask(this, "create").loadInBackground()

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        pendingIntent =
            PendingIntent.getService(
                this, 0, Intent(this, AlarmService::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()
                    + AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR, pendingIntent
        )

        val receiver = ComponentName(this, BootReceive::class.java)

        this.packageManager.setComponentEnabledSetting(
            receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )

        binding.valueRusValuteText.isEnabled = false
        binding.changeValue.isEnabled = false

        adapter = CurrencyAdapter {
            binding.changeValuteName.text = it.Name

            val rubValue = binding.valueRusValuteText.text.toString().toDouble()
            val currency = Converter(rubValue, it.Value, it.Nominal).currency()

            binding.changeValue.text = SpannableStringBuilder(DecimalFormat("#.##").format(currency))

            lastedOnClickCurrency = it
            binding.valueRusValuteText.isEnabled = true
        }

        adapter.valute = currencyRepository.getCurrencyRepository()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.valueRusValuteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                try {
                    val rubValue = binding.valueRusValuteText.text.toString().toDouble()
                    val otherValue = lastedOnClickCurrency.Value
                    val nominal = lastedOnClickCurrency.Nominal
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
        menuInflater.inflate(R.menu.currency_converter_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_update) {
            currencyRepository = FillDBTask(this, "update").loadInBackground()
        }
        return super.onOptionsItemSelected(item)
    }

}
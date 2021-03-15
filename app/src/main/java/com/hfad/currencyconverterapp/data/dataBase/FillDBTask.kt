package com.hfad.currencyconverterapp.data.dataBase

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.loader.content.AsyncTaskLoader
import com.hfad.currencyconverterapp.data.model.Currency
import com.hfad.currencyconverterapp.data.model.CurrencyRepository

/**
 * Fill DB task
 *
 * @property commandAction command what to do this DB
 * @constructor
 *
 * @param context
 */

class FillDBTask(context: Context, private val commandAction: String) : AsyncTaskLoader<CurrencyRepository>(context) {

    /**
     * Load in background
     *
     * this fun get DB and fill currencyRepository
     *
     * @return fill currencyRepository
     */

    @SuppressLint("ShowToast")
    override fun loadInBackground(): CurrencyRepository {
        val currencyRepository = CurrencyRepository()

        try {
            val dbHelper = DBHelper(context)

            if (commandAction == "update") {
                dbHelper.onUpgrade(dbHelper.readableDatabase, dbHelper.readableDatabase.version, 2)
            }

            val dataBase: SQLiteDatabase = dbHelper.readableDatabase

            val cursor = dataBase.query(
                DBHelper.TABLE_NAME, arrayOf("ID", "CharCode", "Name", "Nominal", "Value"),
                null, null, null, null, null
            )

            while (cursor.moveToNext()) {
                val currency = Currency(
                    cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getDouble(4)
                )
                currencyRepository.setCurrency(currency)
            }

            cursor.close()
            dataBase.close()

        } catch (exc: SQLException) {
            Toast.makeText(context,"DataBase unavailable", Toast.LENGTH_SHORT)
        }
        return currencyRepository
    }
}
package com.hfad.currencyconverterapp.data.dataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hfad.currencyconverterapp.data.model.Currency
import com.hfad.currencyconverterapp.data.model.CurrencyRepository
import com.hfad.currencyconverterapp.jsonParser.JsonParser

/**
 * DataBase helper
 *
 * @property context
 * @constructor Create empty DB helper
 */

class DBHelper(private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    /**
     * Companion
     *
     * @constructor Create empty Companion
     */
    companion object {
        const val DB_NAME = "Currency"
        const val DB_VERSION = 1
        const val TABLE_NAME = "currency"
        const val ID = "ID"
        const val CHAR_CODE = "CharCode"
        const val NAME = "Name"
        const val NOMINAL = "Nominal"
        const val VALUE = "Value"
    }

    /**
     * On create
     *
     * Create database
     *
     * @param db
     */

    override fun onCreate(db: SQLiteDatabase?) {

        // table database
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$ID TEXT PRIMARY KEY, $CHAR_CODE TEXT, $NAME TEXT, $NOMINAL INTEGER, $VALUE REAL)"

        db?.execSQL(createTable)

        // insert data
        insertCurrency(db)

    }

    /**
     * On upgrade
     *
     * this fun upgrade database
     *
     * @param db
     * @param oldVersion database
     * @param newVersion database
     */

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (newVersion > 1) {
            db?.execSQL("DELETE FROM $TABLE_NAME") // delete data from this table
            //insert data
            insertCurrency(db)
        }

    }

    /**
     * Insert currency
     *
     * this fun insert data into database
     *
     * @param db
     */

    private fun insertCurrency(db: SQLiteDatabase?) {

        // get currency repos
        val currencyRepository = CurrencyRepository().setRepository(JsonParser.parseJson(JsonParser.URL))

        for (currency: Currency in currencyRepository) { // set content value in DB
            val contentValue = ContentValues()
            contentValue.put(ID, currency.ID)
            contentValue.put(CHAR_CODE, currency.CharCode)
            contentValue.put(NAME, currency.Name)
            contentValue.put(NOMINAL, currency.Nominal)
            contentValue.put(VALUE, currency.Value)

            db?.insert(TABLE_NAME, null, contentValue)
        }
    }

}
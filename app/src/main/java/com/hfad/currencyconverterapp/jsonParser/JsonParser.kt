package com.hfad.currencyconverterapp.jsonParser

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.hfad.currencyconverterapp.data.model.Currency

/**
 *
 * Json parser
 *
 * This class allow to get an object type of Currency, from JSON file.
 *
 * @constructor Create empty Json parser
 */
class JsonParser {

    /**
     * Companion
     *
     * @constructor Create empty Companion
     */

    companion object {

        private const val CURRENCY = "Valute" // name JSON object, when us need

        const val URL = "https://www.cbr-xml-daily.ru/daily_json.js" // URL where is JSON object

        /**
         * parse JSON object
         *
         * @param sUrl URL where is JSON object state
         * @return mutable list type Currency
         */

        fun parseJson(sUrl: String): MutableList<Currency> {

            //get root json object from network
            val root: JsonObject? =
                RetrieveJSONTask().execute(sUrl).get()
            val currencyJsObj = root?.getAsJsonObject(CURRENCY)

            // gets JSON objects in JSON object "Valute"
            val keySetCurrency: Set<String> = currencyJsObj?.keySet() as Set<String>
            val currencyList = mutableListOf<Currency>()

            for (key: String in keySetCurrency) {
                val instanceCurrencyJson: JsonElement = currencyJsObj.get(key)
                val currency = Gson().fromJson(instanceCurrencyJson, Currency::class.java)
                currencyList.add(currency)
            }

            return currencyList
        }

    }

}
package com.hfad.currencyconverterapp.jsonParser

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.hfad.currencyconverterapp.data.model.Currency

class JsonParser {


    companion object { // static fun

        private const val CURRENCY = "Valute"

        const val URL = "https://www.cbr-xml-daily.ru/daily_json.js"

         fun parseJson(sUrl: String): MutableList<Currency> {

            val root: JsonObject = RetrieveJSONTask().execute(sUrl).get()  //get root json object from network
            val currencyJsObj = root.getAsJsonObject(CURRENCY)
            val keySetCurrency: Set<String> = currencyJsObj.keySet()
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
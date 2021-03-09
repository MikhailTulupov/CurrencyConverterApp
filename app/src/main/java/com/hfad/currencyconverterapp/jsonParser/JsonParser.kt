package com.hfad.currencyconverterapp.jsonParser

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.hfad.currencyconverterapp.model.Valute
import java.net.URL
import kotlin.concurrent.thread

class JsonParser {


    companion object { // static fun

        private const val VALUTE = "Valute"

        const val URL = "https://www.cbr-xml-daily.ru/daily_json.js"

         fun parseJson(sUrl: String): MutableList<Valute> {

            val root: JsonObject = RetrieveJSONTask().execute(sUrl).get()  //get root json object from network
            val valuteJsObj = root.getAsJsonObject(VALUTE)
            val keySetValute: Set<String> = valuteJsObj.keySet()
            val valuteList = mutableListOf<Valute>()

            for (key: String in keySetValute) {

                val instanceValuteJson: JsonElement = valuteJsObj.get(key)
                val valute = Gson().fromJson(instanceValuteJson, Valute::class.java)
                valuteList.add(valute)

            }

            return valuteList
        }

    }

}
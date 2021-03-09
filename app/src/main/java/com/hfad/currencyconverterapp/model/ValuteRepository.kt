package com.hfad.currencyconverterapp.model

import com.hfad.currencyconverterapp.jsonParser.JsonParser

class ValuteRepository(
    private val valuteList: MutableList<Valute> = JsonParser.parseJson(JsonParser.URL)
) {

    fun getValuteRepository(): List<Valute> = valuteList

}
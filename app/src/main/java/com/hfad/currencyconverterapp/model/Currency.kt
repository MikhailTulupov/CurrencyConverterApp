package com.hfad.currencyconverterapp.model

class Currency // this class currency rub valute to other valute
    (private val rubValue: Double,
     private val otherValue: Double,
     private val nominal: Int) {

    fun currency(): Double {
        return (rubValue * nominal) / otherValue
    }
}
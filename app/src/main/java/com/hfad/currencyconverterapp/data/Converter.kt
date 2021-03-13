package com.hfad.currencyconverterapp.data

class Converter // this class currency rub currency to other currency
    (private val rubValue: Double,
     private val otherValue: Double,
     private val nominal: Int) {

    fun currency(): Double {
        return (rubValue * nominal) / otherValue
    }
}
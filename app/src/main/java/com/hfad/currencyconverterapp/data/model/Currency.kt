package com.hfad.currencyconverterapp.data.model

/**
 * Currency
 *
 * data class
 *
 * @property ID
 * @property CharCode
 * @property Name
 * @property Nominal
 * @property Value
 * @constructor Create empty Currency
 */

data class Currency(val ID: String, val CharCode: String, val Name: String, val Nominal: Int, val Value: Double)
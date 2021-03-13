package com.hfad.currencyconverterapp.data.model

/**
 * Settable currency
 *
 * This interface get possibility set Repository of Currency
 *
 * @constructor Create empty Settable currency
 */

interface SettableCurrency {
    /**
     * Set repository
     *
     * @param mutableList
     * @return List of Currency
     */
    fun setRepository(mutableList: MutableList<Currency>) : List<Currency>
}
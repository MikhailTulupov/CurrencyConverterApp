package com.hfad.currencyconverterapp.data.model

/**
 * Currency repository
 *
 * @constructor Create empty Currency repository
 */

class CurrencyRepository: SettableCurrency {

    private var currencyList: MutableList<Currency> = mutableListOf()

    /**
     * Get currency repository
     *
     * @return List of Currency
     */
    fun getCurrencyRepository(): List<Currency> = currencyList

    /**
     * Get currency
     *
     * @param id find by id element of List<Currency>
     * @return element of List
     */

    fun getCurrency(id: String) = currencyList.firstOrNull { id == it.ID }

    /**
     * Set currency
     *
     * get possibility set element of List<Currency>
     *
     * @param currency
     */

    fun setCurrency(currency: Currency) {
        currencyList.add(currency)
    }

    /**
     * Set repository
     *
     * @param mutableList
     * @return mutable list
     */
    override fun setRepository(mutableList: MutableList<Currency>) : List<Currency> {
        return mutableList
        }
    }

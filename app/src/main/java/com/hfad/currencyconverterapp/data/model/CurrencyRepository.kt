package com.hfad.currencyconverterapp.data.model

class CurrencyRepository: SettableCurrency {

    private var currencyList: MutableList<Currency> = mutableListOf()

    fun getCurrencyRepository(): List<Currency> = currencyList

    fun getCurrency(id: String) = currencyList.firstOrNull { id == it.ID }

    fun setCurrency(currency: Currency) {
        currencyList.add(currency)
    }

    override fun setRepository(mutableList: MutableList<Currency>) : List<Currency> {
        return mutableList
        }
    }

package com.hfad.currencyconverterapp.jsonParser

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.net.URL

/**
 * Retrieve JSON task
 *
 * Performs the task of establishing a network connection
 * and retrieve a JSON file in the background
 *
 * @property sUrl String with URL
 * @constructor
 *
 * @param context
 */

class RetrieveJSONTask(private val sUrl: String, context: Context) : AsyncTaskLoader<JsonObject>(context) {

    /**
     * Load in background
     *
     * @return Json object
     */

    override fun loadInBackground(): JsonObject? {
        val url = URL(sUrl)
        url.openConnection()
        val inputStreamData = url.openStream().reader()
        return Gson().fromJson(inputStreamData, JsonObject::class.java)
    }

}
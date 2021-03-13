package com.hfad.currencyconverterapp.jsonParser

import android.content.Context
import android.os.AsyncTask
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

class RetrieveJSONTask : AsyncTask<String,Void,JsonObject>() {

    /**
     * do in background
     *
     * @return Json object
     */
    override fun doInBackground(vararg params: String?): JsonObject {
        val url = URL(params[0])
        url.openConnection()
        val inputStreamData = url.openStream().reader()
        return Gson().fromJson(inputStreamData, JsonObject::class.java)
    }

}
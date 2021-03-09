package com.hfad.currencyconverterapp.jsonParser

import android.os.AsyncTask
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.net.URL

class RetrieveJSONTask: AsyncTask<String, Void, JsonObject>() {
    override fun doInBackground(vararg params: String?): JsonObject? {
        val url = URL(params[0])
        url.openConnection()
        val inputStreamData = url.openStream().reader()
        return Gson().fromJson(inputStreamData, JsonObject::class.java)
    }


}
package com.hfad.currencyconverterapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.hfad.currencyconverterapp.data.dataBase.FillDBTask

/**
 * Alarm service
 *
 * this class extends of Service. And help create service for working off lifecycle app
 *
 * @constructor Create empty Alarm service
 */

class AlarmService : Service() {

    /**
     * On start command
     *
     * this fun update DataBase
     *
     * @param intent
     * @param flags
     * @param startId
     * @return super.onStartCommand
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        FillDBTask(this,"update")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}

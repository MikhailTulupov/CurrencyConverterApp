package com.hfad.currencyconverterapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Boot receive
 *
 * this Class create alarm manager if device reboot
 *
 * @constructor Create empty Boot receive
 */

class BootReceive : BroadcastReceiver(), SettableAlarmManager {

    /**
     * On receive
     *
     * this fun create alarm manager and set repeating
     *
     * @param context
     * @param intent
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            setAlarmManager(context!!)
        }
    }
}
package com.hfad.currencyconverterapp.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock

/**
 * Boot receive
 *
 * this Class create alarm manager if device reboot
 *
 * @constructor Create empty Boot receive
 */

class BootReceive : BroadcastReceiver() {

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
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getService(
                context, 0, Intent(context, AlarmService::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()
                        + AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR, pendingIntent
            )
        }
    }
}
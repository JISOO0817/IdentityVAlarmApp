package com.jisoo.identityvalarmapp.alarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.jisoo.identityvalarmapp.util.Const.Companion.BIRTH_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.CHANNEL_ID
import com.jisoo.identityvalarmapp.util.Const.Companion.SWITCH_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.TIME_SP

class App : Application() {

    companion object {
        val ID = CHANNEL_ID
        lateinit var prefs: PrefsManager
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PrefsManager(applicationContext)
        createChannel()
    }

    private fun createChannel() {
        val channel = NotificationChannel(ID, "Alarm Service", NotificationManager.IMPORTANCE_LOW)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

    }

    class PrefsManager(context: Context) {
        private val switchPrefs = context.getSharedPreferences(SWITCH_SP, Context.MODE_PRIVATE)
        private val timePrefs = context.getSharedPreferences(TIME_SP, Context.MODE_PRIVATE)
        private val characBirth = context.getSharedPreferences(BIRTH_SP, Context.MODE_PRIVATE)

        fun setBoolean(key: String, value: Boolean) {
            switchPrefs.edit().putBoolean(key, value).apply()
        }

        fun setCharacBirth(key: String, value: String) {
            characBirth.edit().putString(key, value).apply()
        }

        fun getCharacBirth(key: String, defValue: String): String {
            return characBirth.getString(key, defValue).toString()
        }

        fun setTime(key: String, value: String) {
            timePrefs.edit().putString(key, value).apply()
        }

        fun getTime(key: String, defValue: String): String {
            return timePrefs.getString(key, defValue).toString()
        }

        fun checkPreferencesStatus(): Boolean {
            var status = false
            if (switchPrefs.getBoolean(SWITCH_SP, true)) {
                status = true
            }

            return status
        }

    }


}
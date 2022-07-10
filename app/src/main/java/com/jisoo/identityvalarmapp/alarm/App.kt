package com.jisoo.identityvalarmapp.alarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(ID, "Alarm Service",NotificationManager.IMPORTANCE_HIGH)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    class PrefsManager(context: Context) {
//        private val popupPrefs = context.getSharedPreferences("pop_up",Context.MODE_PRIVATE)
        private val switchPrefs = context.getSharedPreferences(SWITCH_SP,Context.MODE_PRIVATE)
        private val timePrefs = context.getSharedPreferences(TIME_SP,Context.MODE_PRIVATE)

//        fun setString(key: String, value: String) {
//            popupPrefs.edit().putString(key, value).apply()
//        }
//
//        fun getString(key: String, defValue: String) : String {
//            return popupPrefs.getString(key,defValue).toString()
//        }
//
        fun setBoolean(key: String, value: Boolean) {
            switchPrefs.edit().putBoolean(key,value).apply()
        }

        fun getBoolean(key: String, defValue: Boolean) : Boolean {
            return switchPrefs.getBoolean(key,defValue)
        }

        fun setTime(key: String, value: String) {
            timePrefs.edit().putString(key, value).apply()
        }

        fun getTime(key: String, defValue: String): String {
            return timePrefs.getString(key,defValue).toString()
        }

        fun checkPreferencesStatus() : Boolean {
            var status = false
            if(switchPrefs.getBoolean(SWITCH_SP,true)) {
                status = true
            }

            return status
        }

//        fun checkExistTimePrefs() : Boolean {
//            var status = false
//            if(existTimePrefs.getBoolean(TIME_SP,true)) {
//                status = true
//            }
//
//            return status
//        }

    }


}
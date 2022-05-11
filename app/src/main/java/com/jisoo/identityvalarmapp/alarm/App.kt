package com.jisoo.identityvalarmapp.alarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

class App : Application() {

    companion object {
        val ID = "com.jisoo.identityvalarmapp"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(ID, "Alarm Service",NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }


}
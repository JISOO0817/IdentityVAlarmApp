package com.jisoo.identityvalarmapp.alarm

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.main.MainActivity
import com.jisoo.identityvalarmapp.model.CharacInfo

class AlarmService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        try {
            val intent = packageManager.getLaunchIntentForPackage("com.example.myapplication") as Intent
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            Log.d("jjs","service catch")
            val url = "market://details?id=" + "com.jisooZz.haru"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

//https://play.google.com/store/apps/details?id=com.netease.idv.googleplay
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE)

        val notification = NotificationCompat.Builder(this, App.ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("의 생일입니다.")
            .setContentText("알림을 누르면 제5인격 앱으로 이동해요!")
            .setContentIntent(pendingIntent)
            .build()

        Log.d("jjs","의 생일입니다.")

        startForeground(1,notification)
        return START_STICKY

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

}
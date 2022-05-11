package com.jisoo.identityvalarmapp.alarm

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.main.MainActivity
import com.jisoo.identityvalarmapp.model.CharacInfo

class AlarmService : Service() {

    private var list = ArrayList<CharacInfo>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE)

        val notification = NotificationCompat.Builder(this, App.ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("${list.get(0).job}의 생일입니다.")
            .setContentText("알림을 누르면 제5인격 앱으로 이동해요!")
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1,notification)
        return START_STICKY

    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
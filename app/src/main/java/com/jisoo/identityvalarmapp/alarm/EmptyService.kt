package com.jisoo.identityvalarmapp.alarm

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.main.MainActivity
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.Const.Companion.CHANNEL_ID
import com.jisoo.identityvalarmapp.util.Const.Companion.CHANNEL_NAME
import com.jisoo.identityvalarmapp.util.Const.Companion.JOB_KEY
import com.jisoo.identityvalarmapp.util.Const.Companion.UID_KEY

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import kotlin.coroutines.coroutineContext

class EmptyService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("jjs", "EmptyService onStartCommand 호출")

        /**
         * BroadcastReceiver 로 부터 받은 데이터
         * **/
        val characUid = intent.getStringExtra(UID_KEY)
        val characJob = intent.getStringExtra(JOB_KEY)

        /**
         * 시스템 알람창 (기본)
         * **/
        val builder: Notification.Builder =
            Notification.Builder(this, CHANNEL_ID)

        /**
         * 채널생성
         * **/
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)

        val channelManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        channelManager.createNotificationChannel(channel)

        startForeground(100, builder.build())

        val serviceIntent = Intent(this, AlarmService::class.java)
        serviceIntent.putExtra(UID_KEY, characUid)
        serviceIntent.putExtra(JOB_KEY, characJob)
        startService(serviceIntent)

        stopForeground(true)
        stopSelf()

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}
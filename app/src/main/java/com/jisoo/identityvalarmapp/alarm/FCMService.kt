package com.jisoo.identityvalarmapp.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.util.Const

class FCMService : FirebaseMessagingService() {

    private val FCM_CHANNEL_ID = "fcm.channel"

    /**
     * token을 서버로 전송
     * **/
    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {

    }

    /**
     * 수신한 메세지를 처리 ( firebase -> 앱)
     * **/
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification!!.title
        val text = message.notification!!.body

        Log.d("fcm", "title:${title},text:${text}")

        val marketIntent: Intent
        val url = "market://details?id=" + "com.jisoo.identityvalarmapp"
        marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        marketIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val pendingIntent = PendingIntent.getActivity(this, 1111, marketIntent, checkVersionFlags())

        val channel =
            NotificationChannel(
                FCM_CHANNEL_ID,
                Const.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

        val channelManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        channelManager.createNotificationChannel(channel)

        /**
         * fcm 알람
         * **/
        val fcmAlarm = NotificationCompat.Builder(applicationContext, App.ID)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setSmallIcon(versionCheck())
            .setContentIntent(pendingIntent)
            .build()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1000, fcmAlarm)

    }


}

private fun versionCheck(): Int {
    return R.drawable.svg_img
}

private fun checkVersionFlags(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_MUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }
}
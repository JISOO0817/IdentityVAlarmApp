package com.jisoo.identityvalarmapp.alarm

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.model.AlarmRepository
import com.jisoo.identityvalarmapp.model.AlarmRunFunction
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.Const.Companion.BIRTH_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.CHANNEL_ID
import com.jisoo.identityvalarmapp.util.Const.Companion.CHANNEL_NAME
import com.jisoo.identityvalarmapp.util.Const.Companion.JOB_KEY
import com.jisoo.identityvalarmapp.util.Const.Companion.UID_KEY
import kotlinx.coroutines.*

class AlarmService : Service() {
    private val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
    private lateinit var runFunc: AlarmRunFunction
    /**
     * 안드로이드 버전 O부터 noti를 등록할 때 channel id를 등록해야함.
     * (하지 않으면 bad notification for startforeground error 발생)
     * */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("tttttt","AlarmService onStartCommand")
        val uid = intent.getStringExtra(UID_KEY)!!.toInt()
        val job = intent.getStringExtra(JOB_KEY)
        runFunc = AlarmRunFunction(this)
        val pendingIntent: PendingIntent

        val res = resources
        val jobText = String.format(res.getString(R.string.service_noti_title_txt), "$job")

        checkIsInstalled()
        checkSwitchStatus(uid)


        Log.d("tttttt","checkSwitchStatus:${checkSwitchStatus(uid)}")

        /**
         * 알람 누르면
         * **/
        if (!checkIsInstalled()) {
            //마켓
            val playIntent: Intent
            val url =
                resources.getString(R.string.identityv_MarketName) + resources.getString(R.string.identityv_Name)
            playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            playIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            pendingIntent = PendingIntent.getActivity(this, uid, playIntent, checkVersionFlags())

        } else {
            //앱열기
            val goIntent =
                packageManager.getLaunchIntentForPackage(resources.getString(R.string.identityv_Name))
            goIntent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(goIntent)
            pendingIntent = PendingIntent.getActivity(this, uid, goIntent, checkVersionFlags())
        }

        /**
         * 채널생성
         * **/
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )

        val channelManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        channelManager.createNotificationChannel(channel)


        /**
         * 생일알람
         * **/
        val birthAlarm = NotificationCompat.Builder(this, App.ID)
            .setContentTitle(jobText)
            .setContentText(resources.getString(R.string.service_noti_subtitle_txt))
            .setAutoCancel(true)
            .setSmallIcon(versionCheck())
            .setContentIntent(pendingIntent)
            .build()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(uid, birthAlarm)


        /**
         * 알람이 울리고 나서, 오늘 날짜를 sharedPreference 에 저장함
         * **/
        val resultValue = runFunc.getTodayValue()
        App.prefs.setCharacBirth(BIRTH_SP, resultValue.toString())

        coroutineScope.launch {
            val repository = AlarmRepository(application)
            var list: List<CharacInfo> = emptyList()
            repository.getCharacList {
                list = it
            }

            withContext(Dispatchers.Main) {
//                managementAlarm(uid, list)
                runFunc.checkAlarm(list)
                runFunc.removeAlarmManager(uid)
            }
        }
        return START_STICKY
    }


    /**
     * 버전 s 이상인경우 FLAG_MUTABLE 또는 FLAG_IMMUTABLE 이 필수이기 때문에 버전분기로 나눠줌
     * **/
    private fun checkVersionFlags(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }

    /**
     * 제5인격 앱 설치여부 체크
     * **/
    private fun checkIsInstalled(): Boolean {
        return try {
            packageManager.getPackageInfo(
                resources.getString(R.string.identityv_Name),
                PackageManager.GET_ACTIVITIES
            )
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun checkSwitchStatus(uid: Int) {
        if (!App.prefs.checkPreferencesStatus()) {
            runFunc.removeAlarmManager(uid)
        } else {
            return
        }
    }

    /**
     * 노티 아이콘
     * **/
    // 작은 아이콘은 롤리팝 및 상위 안드로이드 버전에서 벡터 아이콘을 써야함
    private fun versionCheck(): Int {
        return R.drawable.svg_img
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    /**
     * 서비스가 죽는경우 서비스를 다시 살림
     * **/
    override fun onDestroy() {
        super.onDestroy()
//        val reviveIntent = Intent()
//        reviveIntent.action = "ACTION.RESTART.AlarmService"
//        sendBroadcast(reviveIntent)
    }

}
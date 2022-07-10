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
import androidx.core.text.isDigitsOnly
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.model.AlarmRepository
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.Const.Companion.CHANNEL_ID
import com.jisoo.identityvalarmapp.util.Const.Companion.CHANNEL_NAME
import com.jisoo.identityvalarmapp.util.Const.Companion.JOB_KEY
import com.jisoo.identityvalarmapp.util.Const.Companion.TIME_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.UID_KEY
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class AlarmService : Service() {

    private val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    /**
     * 안드로이드 버전 O부터 noti를 등록할 때 channel id를 등록해야함.
     * (하지 않으면 bad notification for startforeground error 발생)
     * */

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val uid = intent.getStringExtra(UID_KEY)!!.toInt()
        val job = intent.getStringExtra(JOB_KEY)

        val pendingIntent: PendingIntent

        val res = resources
        val jobText = String.format(res.getString(R.string.service_noti_title_txt), "$job")

        checkIsInstalled()
        Log.d("noti", "is installed: ${checkIsInstalled()}}")

        /**
         * 알람 누르면
         * **/
        if (!checkIsInstalled()) {
            //마켓
            val playIntent: Intent
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val url = resources.getString(R.string.identityv_MarketName) + resources.getString(R.string.identityv_Name)
                playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                playIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            } else {
                Log.d("sdk24","catch 호출")
                playIntent = Intent(Intent.ACTION_VIEW)
                playIntent.data = (Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.identityv_Name)))
            }

            pendingIntent = PendingIntent.getActivity(this, uid, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val url = resources.getString(R.string.identityv_MarketName) + resources.getString(R.string.identityv_Name)
//            val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            playIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            pendingIntent = PendingIntent.getActivity(this, uid, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            //앱열기
            val goIntent = packageManager.getLaunchIntentForPackage(resources.getString(R.string.identityv_Name))
            goIntent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(goIntent)
            pendingIntent = PendingIntent.getActivity(this, uid, goIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }


//        try{
//            Log.d("noti","try호출")
//            val goIntent = packageManager.getLaunchIntentForPackage(resources.getString(R.string.identityv_Name))
//            goIntent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(goIntent)
//            pendingIntent = PendingIntent.getActivity(this, uid, goIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        } catch (e: Exception) {
//            Log.d("noti","catch 호출")
//            val url = resources.getString(R.string.identityv_MarketName)+resources.getString(R.string.identityv_Name)
//            val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//            playIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(playIntent)
//            pendingIntent = PendingIntent.getActivity(this, uid, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        }

        /**
         * 채널생성
         * **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

            val channelManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channelManager.createNotificationChannel(channel)
        }

        /**
         * 생일알람
         * **/
        val birthAlarm = NotificationCompat.Builder(this, App.ID)
            .setContentTitle(jobText)
            .setContentText(resources.getString(R.string.service_noti_subtitle_txt))
            .setAutoCancel(true)
            .setSmallIcon(versionCheck())
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()


        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(uid, birthAlarm)

        coroutineScope.launch {
            val repository = AlarmRepository(application)
            var list: List<CharacInfo> = emptyList()
            repository.getCharacList {
                list = it
            }

            withContext(Dispatchers.Main) {
                managementAlarm(uid, list)
            }
        }
        return START_STICKY
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

    /**
     * 노티 아이콘
     * **/
    // 작은 아이콘은 롤리팝 및 상위 안드로이드 버전에서 벡터 아이콘을 써야함
    private fun versionCheck(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            R.drawable.svg_img
        } else {
            R.drawable.app_icon_ver2
        }
    }

    private fun managementAlarm(uid: Int, alarmList: List<CharacInfo>) {
        val sortedList = getNearestBirthday(alarmList)

        val calendar = setCalendarInfo(sortedList)

        val Cuid = sortedList[0].uid
        val Cjob = sortedList[0].job

        val intent2 = Intent(this, AlarmBroadcast::class.java)
        Intent().also {
            intent2.putExtra(UID_KEY, Cuid.toString())
            intent2.putExtra(JOB_KEY, Cjob)
        }

        val pendingIntent2 = PendingIntent.getBroadcast(
            this,
            Cuid.toInt(),
            intent2,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager: AlarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent2
            )
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent2)
        }
        Log.d("samsung", "새로 등록된 알람 $Cjob")

        removeAlarmManager(uid)
    }

    private fun getNearestBirthday(alarmList: List<CharacInfo>): List<CharacInfo> {
        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1
        val nowDay = now.get(Calendar.DAY_OF_MONTH)

        var resultValue = nowMonth * 100 + nowDay
        val resultList: ArrayList<CharacInfo> = ArrayList()

        if (nowMonth == 12 && nowDay >= 27) {
            resultValue = 0
        }

        for (bi in alarmList) {
            if (bi.birth.toInt() > resultValue) {
                resultList.add(bi)
            }
        }

        val sortedList: ArrayList<CharacInfo> = ArrayList()

        if (nowMonth == 12 && nowDay >= 25) {
            sortedList.add(resultList[0])
        } else {
            val day1 = resultList[0].birth.substring(2 until 4).toInt()
            val day2 = resultList[1].birth.substring(2 until 4).toInt()

            if (day1 == day2) {
                sortedList.add(resultList[0])
                sortedList.add(resultList[1])
            } else {
                sortedList.add(resultList[0])
            }
        }

        return sortedList
    }

    private fun setCalendarInfo(sortedList: List<CharacInfo>): Calendar {
        val month = sortedList[0].birth.substring(0 until 2).toInt()
        val day = sortedList[0].birth.substring(2 until 4).toInt()

        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1

        val checkNumBol = month.toString().isDigitsOnly()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        if (checkNumBol) {
            calendar.set(Calendar.MONTH, month - 1)
        }

        val prefsTime = App.prefs.getTime(TIME_SP, "")
        val arr = prefsTime.split(" : ")

        val hour = arr[0].toInt()
        val minute = arr[1].toInt()

        if (nowMonth == 12 && month == 1) {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
        } else {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR])
        }
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar
    }

    /**
     * 노티를 띄우고난 후 기존에 등록했던(uid비교) 알람매니저 삭제
     * **/
    private fun removeAlarmManager(removeUid: Int) {
        val alarmManager: AlarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, AlarmBroadcast::class.java)
        val amPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            removeUid,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(amPendingIntent)
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
//        reviveIntent.flags =
    }

}
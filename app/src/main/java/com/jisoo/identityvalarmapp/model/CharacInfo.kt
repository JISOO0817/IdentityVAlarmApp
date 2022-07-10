package com.jisoo.identityvalarmapp.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jisoo.identityvalarmapp.alarm.AlarmBroadcast
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.util.Const.Companion.JOB_KEY
import com.jisoo.identityvalarmapp.util.Const.Companion.UID_KEY
import kotlinx.android.parcel.Parcelize
import java.util.*


/**
 * 앱 데이터베이스의 테이블을 나타냄
 * */

@Entity
@Parcelize
data class CharacInfo(
    @PrimaryKey val uid: Long,
    @ColumnInfo var category: Int,
    @ColumnInfo var img: Int,
    @ColumnInfo var job: String,
    @ColumnInfo var birth: String
) : Parcelable {


    @SuppressLint("SimpleDateFormat")
    fun executionAlarm(context: Context, uid: Int, job: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmBroadcast::class.java)

        intent.putExtra(UID_KEY,uid.toString())
        intent.putExtra(JOB_KEY,job)

        Log.d("jjs","uid:${uid},job:${job}")

        val pendingIntent = PendingIntent.getBroadcast(context, uid,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val month = birth.substring(0 until 2).toInt()
        val day = birth.substring(2 until 4).toInt()

        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1

        val checkNumBol = month.toString().isDigitsOnly()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        if(checkNumBol) {
            calendar.set(Calendar.MONTH,month-1)
        }

        val prefsTime = App.prefs.getTime("time","")
        val arr = prefsTime.split(" : ")

        val hour = arr[0].toInt()
        val minute = arr[1].toInt()

        if(nowMonth == 12 && month == 1) {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
        } else {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR])
        }
        calendar.set(Calendar.DAY_OF_MONTH,day)
        calendar.set(Calendar.HOUR_OF_DAY,hour)
        calendar.set(Calendar.MINUTE,minute)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)

        /**
         * 버전에 따른 Doze 모드 구분
         * **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)
        }
    }

    fun removeAlarmManager(context: Context, removeUid: Int) {
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcast::class.java)
        val amPendingIntent = PendingIntent.getBroadcast(
            context,
            removeUid,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(amPendingIntent)
    }

}
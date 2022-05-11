package com.jisoo.identityvalarmapp.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jisoo.identityvalarmapp.alarm.AlarmBroadcast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity

/**
 * 앱 데이터베이스의 테이블을 나타냄
 * */

data class CharacInfo(
    @PrimaryKey val uid: Long?,
    @ColumnInfo var category: Int,
    @ColumnInfo var img: Int,
    @ColumnInfo var job: String,
    @ColumnInfo var birth: String,
    @ColumnInfo var onoff: Boolean
) {


    @SuppressLint("SimpleDateFormat")
    fun executionAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,uid?.toInt()!!,intent,PendingIntent.FLAG_IMMUTABLE)


        val month = birth.substring(0 until 2).toInt()
        val day = birth.substring(3 until 5).toInt()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.YEAR,calendar[Calendar.YEAR])
        calendar.set(Calendar.MONTH,month-1)
        calendar.set(Calendar.DAY_OF_MONTH,day)
        calendar.set(Calendar.HOUR_OF_DAY,13)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        calendar.set(Calendar.MILLISECOND,0)


        if(calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) + 1)
        }

//        val simpleFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
//        Log.d("jjs","날짜변환:${simpleFormat.format(calendar.timeInMillis)},현재시스템:${simpleFormat.format(System.currentTimeMillis())}")
//        Log.d("jjs","년도:${calendar.get(Calendar.YEAR)}")
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)

        onoff = true
    }

    fun cancelAlarm(context: Context) {
        val alarmManager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,AlarmBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,uid?.toInt()!!,intent,PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)

        onoff = false
    }

}
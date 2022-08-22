package com.jisoo.identityvalarmapp.model

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.jisoo.identityvalarmapp.alarm.AlarmBroadcast
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.util.CalendarHelper
import com.jisoo.identityvalarmapp.util.Const
import com.jisoo.identityvalarmapp.util.Const.Companion.BIRTH_SP
import java.util.*

data class AlarmRunFunction(val context: Context) {

    fun returnBySortingTheList(list: List<CharacInfo>): List<CharacInfo> {
        val returnList = lunaList2SolarList(list)
        return returnList
    }

    fun executionAlarm(uid: Int, job: Int, birth: String) {
        Log.d("tett","execdutuionAlarm 호출 uid:${uid},job:${job}")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcast::class.java)

        intent.putExtra(Const.UID_KEY, uid.toString())
        intent.putExtra("job", job.toString())

        Log.d("tett","executionAlarm uid:${uid},job:${job}")

        val pendingIntent = PendingIntent.getBroadcast(context, uid, intent, checkVersionFlags())

        val month = birth.substring(0 until 2).toInt()
        val day = birth.substring(2 until 4).toInt()

        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1

        val checkNumBol = month.toString().isDigitsOnly()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        if (checkNumBol) {
            calendar.set(Calendar.MONTH, month - 1)
        }

        val prefsTime = App.prefs.getTime(Const.TIME_SP, "")
        val arr: List<String> = prefsTime.split(":")

        // sharedpreference 저장된 값에 띄어쓰기(  )공백 포함된 경우 삭제하는 로직 추가
        var hourStr = arr[0]
        var minuteStr = arr[1]

        val hourIdx:Int = arr[0].indexOf(" ")
        val minuteIdx:Int = arr[1].indexOf(" ")

        if(hourStr.contains(" ")) {
            hourStr = hourStr.substring(0,hourIdx)
        }

        if(minuteStr.contains(" ")) {
            minuteStr = minuteStr.substring(minuteIdx+1)
        }

        val hourInt = hourStr.toInt()
        val minuteInt = minuteStr.toInt()

        if (nowMonth == 12 && month == 1) {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR] + 1)
        } else {
            calendar.set(Calendar.YEAR, calendar[Calendar.YEAR])
        }

        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.HOUR_OF_DAY, hourInt)
        calendar.set(Calendar.MINUTE, minuteInt)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        /**
         * 버전에 따른 Doze 모드 구분
         * **/
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun checkVersionFlags(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
    }

    fun checkAlarm(list: List<CharacInfo>) {
        /** 스위치가 OFF 인 경우 return **/
        if (!App.prefs.checkPreferencesStatus()) {
            return
        }
        val temList: List<CharacInfo> = lunaList2SolarList(list)
        val closestCharacter: List<CharacInfo> = getClossetCharacList(temList)

        for (i in closestCharacter.indices) {
            executionAlarm(
                closestCharacter[i].uid,
                closestCharacter[i].job,
                closestCharacter[i].birth
            )

            Log.d("tett","alarmRunFunction checkAlarm uid: ${closestCharacter[i].uid},job: ${closestCharacter[i].job}")
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getClossetCharacList(sortedlist: List<CharacInfo>): List<CharacInfo> {
        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1
        val nowDay = now.get(Calendar.DAY_OF_MONTH)
        var nowValue = getTodayValue()

        val birthSP = App.prefs.getCharacBirth(BIRTH_SP,"")

        val futureList: ArrayList<CharacInfo> = arrayListOf()
        val nearList: ArrayList<CharacInfo> = arrayListOf()

        if (nowMonth == 12 && nowDay >= 27) {
            nowValue = 0
        }

        if (birthSP.isEmpty()) {

            for (data in sortedlist) {
                if (data.birth.toInt() >= nowValue) {
                    futureList.add(data)
                }
            }

            val birthString = futureList[0].birth

            for(data in futureList) {
                if(birthString == data.birth) {
                    nearList.add(data)
                } else {
                    break
                }
            }

            if(nearList.isEmpty()) {
                nearList.add(futureList[0])
            }

            return nearList

        } else {

            for (data in sortedlist) {
                if (data.birth.toInt() >= nowValue) {
                    futureList.add(data)
                }
            }

            val temList: ArrayList<CharacInfo> = arrayListOf()
            for (i in futureList) {
                if (TextUtils.equals(birthSP.toInt().toString(), i.birth.toInt().toString())) {
                    temList.add(i)
                }
            }

            futureList.removeAll(temList.toSet())

            val birthString = futureList[0].birth

            for(data in futureList) {
              if(birthString == data.birth) {
                  nearList.add(data)
              } else {
                  break
              }
            }

            if(nearList.isEmpty()) {
                nearList.add(futureList[0])
            }

            return nearList

        }
    }

    fun lunaList2SolarList(list: List<CharacInfo>): List<CharacInfo> {
        val calendarHelper = CalendarHelper()
        val returnList: ArrayList<CharacInfo> = arrayListOf()

        val now = Calendar.getInstance()
        val nowYear = now.get(Calendar.YEAR).toString()
        for (i in list) {
            if (TextUtils.equals(i.uid.toString(), "106")) {
                val characBirth = calendarHelper.lunar2Solar(nowYear + i.birth).substring(4 until 8)
                returnList.add(CharacInfo(i.uid, i.category, i.img, i.job, characBirth))
            } else {
                returnList.add(i)
            }
        }
        return returnList.sortedBy(CharacInfo::birth)
    }

    fun removeAlarmManager(removeUid: Int) {
        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmBroadcast::class.java)
        val amPendingIntent = PendingIntent.getBroadcast(
            context,
            removeUid,
            intent,
            checkVersionFlags()
        )
        alarmManager.cancel(amPendingIntent)
    }

    fun getTodayValue(): Int {
        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1
        val nowDay = now.get(Calendar.DAY_OF_MONTH)

        return nowMonth * 100 + nowDay
    }

}
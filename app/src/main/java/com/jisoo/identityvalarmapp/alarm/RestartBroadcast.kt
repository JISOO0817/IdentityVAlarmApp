package com.jisoo.identityvalarmapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.room.Room
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.model.AlarmDatabase
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.CalendarHelper
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * 알람매니저에 알람이 등록되어있어도,
 * 기기가 재부팅이 되면 등록된 알람이 삭제되므로, 재설정해줌
 * **/
class RestartBroadcast : BroadcastReceiver() {

    private val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.action

        /**
         * 서비스가 죽었을 때
         * 재부팅한 시간을 기준으로 가장 가까운 날짜에 생일인 캐릭터를
         * 등록해준다.
         * **/
        if(TextUtils.equals(action,"ACTION.RESTART.AlarmService")) {
            Log.d("jjs", "ACTION.RESTART.AlarmService")

            if (context != null) {
                setAlarmmanager(context)
            }
        }

        /**startForegroundService
         * 폰 재시작할 때 알람매니저 재등록
         * 재부팅한 시간을 기준으로 가장 가까운 날짜에 생일인 캐릭터를
         * 등록해준다.
         * **/

        if (TextUtils.equals(action, Intent.ACTION_BOOT_COMPLETED)) {
            Log.d("jjs", "BOOT_COMPLETED event")

            if (context != null) {
                setAlarmmanager(context)
            }
        }
    }

    private fun getClossetCharacList(list: List<CharacInfo>): List<CharacInfo> {
        val sortedList: ArrayList<CharacInfo> = ArrayList()
        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1
        val nowDay = now.get(Calendar.DAY_OF_MONTH)
        var resultValue = nowMonth * 100 + nowDay

        val resultList: ArrayList<CharacInfo> = ArrayList()

        if (nowMonth == 12 && nowDay >= 27) {
            resultValue = 0
        }

        for (bi in list) {
            if (bi.birth.toInt() > resultValue) {
                Log.d("TAG", "bi.birth.toInt:${bi.birth.toInt()},resultValue:${resultValue}")
                resultList.add(bi)
            }
        }

        Log.d("TAG", "resultList:${resultList}")

        if (nowMonth == 12 && nowDay >= 25) {
            sortedList.add(resultList[0])
        } else {
            val day1 = resultList[0].birth.substring(2 until 4).toInt()
            val day2 = resultList[1].birth.substring(2 until 4).toInt()

            Log.d("TAG", "day1:${day1},day2:${day2}")

            if (day1 == day2) {
                sortedList.add(resultList[0])
                sortedList.add(resultList[1])
            } else {
                sortedList.add(resultList[0])
            }
        }

        return sortedList
    }

    private fun setAlarmmanager(context: Context) {
        coroutineScope.launch {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AlarmDatabase::class.java,
                context.packageName,
            ).build()

            val list = db.alarmDao().getAllDataList()

            withContext(Dispatchers.Main) {
                val calendarHelper = CalendarHelper()

                val returnList: ArrayList<CharacInfo> = arrayListOf()
                val now = Calendar.getInstance()
                val nowYear = now.get(Calendar.YEAR).toString()
                for (i in list) {
                    if (TextUtils.equals(i.job, "우산의영혼")) {
                        val characBirth =
                            calendarHelper.lunar2Solar(nowYear + i.birth).substring(4 until 8)
                        returnList.add(CharacInfo(i.uid, i.category, i.img, i.job, characBirth))
                    } else {
                        returnList.add(i)
                    }
                }

                val closestCharacter: List<CharacInfo> = getClossetCharacList(returnList.sortedBy(CharacInfo::birth))
                for (i in closestCharacter) {
                    i.executionAlarm(context, i.uid.toInt(), i.job)
                    Log.d("jjs", "uid:${i.uid.toInt()},job:${i.job}")
                }
            }
        }
    }
}

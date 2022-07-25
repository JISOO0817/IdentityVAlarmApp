package com.jisoo.identityvalarmapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import androidx.room.Room
import com.jisoo.identityvalarmapp.model.AlarmDatabase
import com.jisoo.identityvalarmapp.model.AlarmRunFunction
import kotlinx.coroutines.*

/**
 * 알람매니저에 알람이 등록되어있어도,
 * 기기가 재부팅이 되면 등록된 알람이 삭제되므로, 재설정해줌
 * **/
class RestartBroadcast : BroadcastReceiver() {

    private val coroutineScope by lazy { CoroutineScope(Dispatchers.IO) }
    private lateinit var runFunc: AlarmRunFunction

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

    private fun setAlarmmanager(context: Context) {
        runFunc = AlarmRunFunction(context)
        coroutineScope.launch {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AlarmDatabase::class.java,
                context.packageName,
            ).build()

            val list = db.alarmDao().getAllDataList()

            withContext(Dispatchers.Main) {
                runFunc.checkAlarm(list)
            }
        }
    }

}

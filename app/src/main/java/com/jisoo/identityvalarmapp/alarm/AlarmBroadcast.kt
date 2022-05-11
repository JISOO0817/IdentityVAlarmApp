package com.jisoo.identityvalarmapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class AlarmBroadcast : BroadcastReceiver() {

    /**
     * 알람매니저에 의해 호출이된다.
     * 호출이되면 무엇을 할 것인지 onReceive 메서드 안에 정의함
     * */

    override fun onReceive(context: Context?, intent: Intent?) {
        val intentService = Intent(context, AlarmService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context?.startForegroundService(intentService)
        } else {
            context?.startService(intentService)
        }
    }

}
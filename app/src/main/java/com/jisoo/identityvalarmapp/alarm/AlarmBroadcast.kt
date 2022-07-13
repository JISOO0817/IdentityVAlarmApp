package com.jisoo.identityvalarmapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.jisoo.identityvalarmapp.util.Const.Companion.JOB_KEY
import com.jisoo.identityvalarmapp.util.Const.Companion.UID_KEY

class AlarmBroadcast : BroadcastReceiver() {

    /**
     * 알람매니저에 의해 호출이된다.
     * 호출이되면 무엇을 할 것인지 onReceive 메서드 안에 정의함
     * */
    override fun onReceive(context: Context?, intent: Intent) {

        val uid = intent.getStringExtra(UID_KEY)
        val job = intent.getStringExtra(JOB_KEY)

        val serviceIntent = Intent(context, EmptyService::class.java)

        serviceIntent.putExtra(UID_KEY,uid)
        serviceIntent.putExtra(JOB_KEY,job)

        context?.startForegroundService(serviceIntent)
    }
}
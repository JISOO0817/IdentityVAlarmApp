package com.jisoo.identityvalarmapp.model

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.*

class AlarmRepository(application: Application) {

    private var alarmDao: AlarmDao
    var characList: LiveData<List<CharacInfo>>

    init {
        this.alarmDao = AlarmDatabase.getInstance(application).alarmDao()
        characList = alarmDao.getAllData()
    }

    fun getCharacList(callback: (List<CharacInfo>) -> Unit) {
        callback(alarmDao.getAllDataList())
    }

}
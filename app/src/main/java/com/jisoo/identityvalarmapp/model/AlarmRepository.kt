package com.jisoo.identityvalarmapp.model

import android.app.Application
import androidx.lifecycle.LiveData

class AlarmRepository(application: Application) {

    private var alarmDao: AlarmDao
    var surList: LiveData<List<CharacInfo>>
    var hunList: LiveData<List<CharacInfo>>

    init {
        this.alarmDao = AlarmDatabase.getInstance(application).alarmDao()
        this.surList = alarmDao.getSurvivorDataList()
        this.hunList = alarmDao.getHunterDataList()
    }

    suspend fun insert(info: CharacInfo) {
        alarmDao.insert(info)
    }

    suspend fun update(info: CharacInfo) {
        alarmDao.update(info)
    }

    suspend fun delete(info: CharacInfo) {
        alarmDao.delete(info)
    }
}
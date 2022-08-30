package com.jisoo.identityvalarmapp.model

import androidx.lifecycle.LiveData

import javax.inject.Inject


class AlarmRepository @Inject constructor(private val alarmDao: AlarmDao) {

    fun getAllData(): LiveData<List<CharacInfo>> {
        return alarmDao.getAllDataList()
    }

    suspend fun getCharacList(callback: (List<CharacInfo>) -> Unit) {
        callback(alarmDao.getAllData())
    }


}
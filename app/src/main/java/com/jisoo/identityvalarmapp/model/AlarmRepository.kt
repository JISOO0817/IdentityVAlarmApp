package com.jisoo.identityvalarmapp.model

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AlarmRepository(application: Application) {

    private var alarmDao: AlarmDao = AlarmDatabase.getInstance(application).alarmDao()
//    var characList: LiveData<List<CharacInfo>> = alarmDao.getAllData()

    fun getAllData(): LiveData<List<CharacInfo>> {
        return alarmDao.getAllDataList()
    }

    suspend fun getCharacList(callback: (List<CharacInfo>) -> Unit) {
        callback(alarmDao.getAllData())
    }


}
package com.jisoo.identityvalarmapp.model

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AlarmRepository(application: Application) {

    private var alarmDao: AlarmDao = AlarmDatabase.getInstance(application).alarmDao()
//    var characList: LiveData<List<CharacInfo>> = alarmDao.getAllData()

    suspend fun getAllData(): List<CharacInfo> {
        return withContext(Dispatchers.IO) {
            alarmDao.getAllData()
        }
    }

    fun getCharacList(callback: (List<CharacInfo>) -> Unit): Unit {
        callback(alarmDao.getAllData())
    }

}
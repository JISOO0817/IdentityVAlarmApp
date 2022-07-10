//package com.jisoo.identityvalarmapp.model
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.LiveData
//
//class TimeRepository(application: Application) {
//
//    private var timeDao: TimeDao
//
//    var timeInfo: LiveData<List<Time>>
//
//    init {
//        this.timeDao = AlarmDatabase.getInstance(application).timeDao()
//        timeInfo = timeDao.getAllTimeData()
//    }
//
//    suspend fun insert(time: Time) {
//        timeDao.insert(time)
//    }
//
//    suspend fun update(time: Time) {
//        Log.d("time","timeRepo update호출")
//        timeDao.update(time)
//    }
//
//}
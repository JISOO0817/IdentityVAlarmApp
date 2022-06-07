package com.jisoo.identityvalarmapp.main

import android.app.Application
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jisoo.identityvalarmapp.model.AlarmRepository
import com.jisoo.identityvalarmapp.model.CharacInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application){

    private var repository : AlarmRepository = AlarmRepository(application)
    var surList : LiveData<List<CharacInfo>> = repository.surList
    var hunList: LiveData<List<CharacInfo>> = repository.hunList

    private val _beforeSurList = MutableLiveData<List<CharacInfo>>()
    val beforeSurList : LiveData<List<CharacInfo>>
        get() = _beforeSurList

    private val _afterSurList = MutableLiveData<List<CharacInfo>>()
    val afterSurList : LiveData<List<CharacInfo>>
        get() = _afterSurList


    private val _checkClicked = MutableLiveData<Boolean>()
    val checkClicked: LiveData<Boolean>
        get() = _checkClicked


    init {
        _checkClicked.value = false
    }


    //TODO: 생존자 리스트에서 생일이 지난 캐릭터와 지나지 않은 캐릭터를 구분하여 리사이클러뷰에 담아주기. (새로 생성)

    fun init() {
        Log.d("jjs","생존자 리스트 사이즈:${surList.value!!.size}")
        checkBirthDayPassed()
    }

    fun checkBirthDayPassed() {
        val afterList: ArrayList<CharacInfo> = ArrayList()
        val beforeList: ArrayList<CharacInfo> = ArrayList()
        for( charac in surList.value!!) {

            val month = charac.birth.substring(0 until 2).toInt()
            val day = charac.birth.substring(3 until 5).toInt()
            val checkNumBol = month.toString().isDigitsOnly()

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.YEAR,calendar[Calendar.YEAR])

            if(checkNumBol) {
                calendar.set(Calendar.MONTH,month-1)
            }

            calendar.set(Calendar.DAY_OF_MONTH,day)
            calendar.set(Calendar.HOUR_OF_DAY,24)
            calendar.set(Calendar.MINUTE,0)
            calendar.set(Calendar.SECOND,0)
            calendar.set(Calendar.MILLISECOND,0)

            if(calendar.timeInMillis < System.currentTimeMillis()) {
                afterList.add(charac)
                calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) + 1)
            } else {
                beforeList.add(charac)
            }
            Log.d("jjs","character : ${charac.job}, birth : ${charac.birth}")
        }

        _afterSurList.value = afterList
        _beforeSurList.value = beforeList
    }


    fun update(info: CharacInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(info)
        }
    }


    fun onCheckBoxClicked() {
        _checkClicked.value = true
    }

    override fun onCleared() {
        super.onCleared()
    }

}
package com.jisoo.identityvalarmapp.main

import android.annotation.SuppressLint
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: AlarmRepository = AlarmRepository(application)
    var surList: LiveData<List<CharacInfo>> = repository.surList
    var hunList: LiveData<List<CharacInfo>> = repository.hunList

    private val _todaySurList = MutableLiveData<List<CharacInfo>>()
    val todaySurList: LiveData<List<CharacInfo>>
        get() = _todaySurList

    private val _beforeSurList = MutableLiveData<List<CharacInfo>>()
    val beforeSurList: LiveData<List<CharacInfo>>
        get() = _beforeSurList

    private val _passedSurList = MutableLiveData<List<CharacInfo>>()
    val passedSurList: LiveData<List<CharacInfo>>
        get() = _passedSurList


    private val _checkClicked = MutableLiveData<Boolean>()
    val checkClicked: LiveData<Boolean>
        get() = _checkClicked


    init {
        _checkClicked.value = false
    }

    @SuppressLint("SimpleDateFormat")
    fun checkBirthDayPassed() {
        val passedList: ArrayList<CharacInfo> = ArrayList()
        val beforeList: ArrayList<CharacInfo> = ArrayList()
        val todayList: ArrayList<CharacInfo> = ArrayList()

        val systemDate = System.currentTimeMillis()
        val mDate = Date(systemDate)
        val simpleDate = SimpleDateFormat("MM-dd")
        val getDate = simpleDate.format(mDate)

        val sysMonth = getDate.substring(0 until 2).toInt()
        val sysDay = getDate.substring(3 until 5).toInt()

        for (charac in surList.value!!) {

            val birthMonth = charac.birth.substring(0 until 2).toInt()
            val bDay = charac.birth.substring(3 until 5).toInt()

            when {
                birthMonth < sysMonth -> {
                    passedList.add(charac)
                }
                birthMonth == sysMonth -> {
                    when {
                        bDay < sysDay -> {
                            passedList.add(charac)
                        }
                        bDay == sysDay -> {
                            todayList.add(charac)
                        }
                        else -> {
                            beforeList.add(charac)
                        }
                    }
                }
                else -> {
                    beforeList.add(charac)
                }
            }
        }

        _todaySurList.value = todayList
        _passedSurList.value = passedList
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
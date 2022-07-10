package com.jisoo.identityvalarmapp.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.model.AlarmRepository
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.CalendarHelper

import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: AlarmRepository = AlarmRepository(application)
    var characList: LiveData<List<CharacInfo>> = repository.characList

    private val _sortedSolarList = MutableLiveData<List<CharacInfo>>()
    val sortedSolarList: LiveData<List<CharacInfo>> = _sortedSolarList

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _hour = MutableLiveData<String>()
    val hour: LiveData<String> = _hour

    private val _minute = MutableLiveData<String>()
    val minute: LiveData<String> = _minute

    private val _switchClicked = MutableLiveData<Boolean>()
    val switchClicked: LiveData<Boolean> = _switchClicked

    private val _alarmStatusText = MutableLiveData<String>()
    val alarmStatusText: LiveData<String> = _alarmStatusText

    private val _reviewClicked = MutableLiveData<Boolean>()
    val reviewClicked: LiveData<Boolean> = _reviewClicked

    private val _feedbackClicked = MutableLiveData<Boolean>()
    val feedbackClicked: LiveData<Boolean> = _feedbackClicked

    private val _onTimeEditClicked = MutableLiveData<Boolean>()
    val onTimeEditClicked: LiveData<Boolean> = _onTimeEditClicked

    private val _onCloseBtnClicked = MutableLiveData<Boolean>()
    val onCloseBtnClicked: LiveData<Boolean> = _onCloseBtnClicked

    private val _onConfirmBtnClicked = MutableLiveData<Boolean>()
    val onConfirmBtnClicked: LiveData<Boolean> =  _onConfirmBtnClicked

    private val _onWarningCloseBtnClicked = MutableLiveData<Boolean>()
    val onWarningClostBtnCLicked: LiveData<Boolean> = _onWarningCloseBtnClicked

    private val _onNeedUpdateConfirmBtnClicked = MutableLiveData<Boolean>()
    val onNeedUpdateConfirmBtnClicked: LiveData<Boolean> = _onNeedUpdateConfirmBtnClicked

    private val _timeSet = MutableLiveData<Boolean>()
    val timeSet: LiveData<Boolean> = _timeSet

    private val _appVer = MutableLiveData<String>()
    val appVer: LiveData<String> = _appVer

    private val _finishFlag = MutableLiveData<Boolean>()
    val finishFlag: LiveData<Boolean> = _finishFlag

    private val _toastFlag = MutableLiveData<Boolean>()
    val toastFlag: LiveData<Boolean> = _toastFlag

    private var backPressedTime: Long = 0

    init {
        _reviewClicked.value = false
        _feedbackClicked.value = false
        _onTimeEditClicked.value = false
        _onCloseBtnClicked.value = false
        _onWarningCloseBtnClicked.value = false
        _onNeedUpdateConfirmBtnClicked.value = false
        _onConfirmBtnClicked.value = false
        _timeSet.value = false
        _finishFlag.value = false
        _toastFlag.value = false
        _appVer.value = application.packageManager.getPackageInfo(application.packageName, 0).versionName

        initSwitchStatusText()
    }

    fun onSwitchClicked() {
        _switchClicked.value = !(App.prefs.checkPreferencesStatus())
        if (App.prefs.checkPreferencesStatus()) {
            _alarmStatusText.value =
                getApplication<Application>().resources.getString(R.string.fragment_setting_alarm_on_txt)
        } else {
            _alarmStatusText.value =
                getApplication<Application>().resources.getString(R.string.fragment_setting_alarm_off_txt)
        }
    }

    fun initSwitchStatusText() {
        if (App.prefs.checkPreferencesStatus()) {
            _alarmStatusText.value =
                getApplication<Application>().resources.getString(R.string.fragment_setting_alarm_on_txt)
        } else {
            _alarmStatusText.value =
                getApplication<Application>().resources.getString(R.string.fragment_setting_alarm_off_txt)
        }
    }

    /**
     * 캐릭터 '우산' 의 경우 생일이 음력이므로,
     * 양력으로 변환하여 다시 리스트에 넣어줌
     * **/
    fun lunaList2SolarList(list: List<CharacInfo>) {
        val calendarHelper = CalendarHelper()
        val returnList: ArrayList<CharacInfo> = arrayListOf()

        val now = Calendar.getInstance()
        val nowYear = now.get(Calendar.YEAR).toString()
        for (i in list) {
            if (TextUtils.equals(i.job, "우산의영혼")) {
                val characBirth = calendarHelper.lunar2Solar(nowYear + i.birth).substring(4 until 8)
                returnList.add(CharacInfo(i.uid, i.category, i.img, i.job, characBirth))
            } else {
                returnList.add(i)
            }
        }
        _sortedSolarList.value = returnList.sortedBy(CharacInfo::birth)
    }

    fun checkAlarm(context: Context, list: List<CharacInfo>) {
        if (!App.prefs.checkPreferencesStatus()) {
            return
        }

        val closestCharacter: List<CharacInfo> = getClossetCharacList(list)
        for (c_list in closestCharacter) {
            c_list.executionAlarm(context, c_list.uid.toInt(), c_list.job)
            Log.d("toggle", "turnOnTheAlarm 등록된 알람매니저 정보 :${c_list.job}, ${c_list.uid}")
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getClossetCharacList(list: List<CharacInfo>): List<CharacInfo> {
        val sortedList: ArrayList<CharacInfo> = ArrayList()
        val now = Calendar.getInstance()
        val nowMonth = now.get(Calendar.MONTH) + 1
        val nowDay = now.get(Calendar.DAY_OF_MONTH)
        var resultValue = nowMonth * 100 + nowDay

        val resultList: ArrayList<CharacInfo> = ArrayList()

        if (nowMonth == 12 && nowDay >= 27) {
            resultValue = 0
        }

        Log.d("test","받아온 list 값:${list}")

        for (bi in list) {
            if (bi.birth.toInt() > resultValue) {
                resultList.add(bi)
            }
        }

        Log.d("test","resultList[0]:${resultList[0].job}")

        if (nowMonth == 12 && nowDay >= 25) {
            sortedList.add(resultList[0])
        } else {
            //TODO 자꾸 IndexOutOfBoundsException 에러남 (맨 초기) resultList size 0
            val day1 = resultList[0].birth.substring(2 until 4).toInt()
            val day2 = resultList[1].birth.substring(2 until 4).toInt()

            if (day1 == day2) {
                sortedList.add(resultList[0])
                sortedList.add(resultList[1])
            } else {
                sortedList.add(resultList[0])
            }
        }

        return sortedList
    }


    fun writeAReview() {
        _reviewClicked.value = true
    }

    fun feedbackView() {
        _feedbackClicked.value = true
    }

    fun onAlarmTimeEditClicked() {
        _onTimeEditClicked.value = true
    }

    fun onEditDialogCloseBtnClicked() {
        _onCloseBtnClicked.value = true
    }

    fun onEditDialogConfirmBtnClicked() {
        _onConfirmBtnClicked.value = true
    }

    fun onWarningDialogClosetBtnClicked() {
        _onWarningCloseBtnClicked.value = true
    }

    fun onNeedUpdateDialogConfirmBtnClicked() {
        _onNeedUpdateConfirmBtnClicked.value = true
    }

    fun setTime(hour: String?, minute: String?) {
        _hour.value = hour!!
        _minute.value = minute!!
        _time.value = hour + minute
    }

    fun onBackPressedMethod() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            _toastFlag.value =true
            _finishFlag.value =false
            return
        }
        if (System.currentTimeMillis() <= backPressedTime + 2000) {
            _finishFlag.value = true
            _toastFlag.value = false
        }
    }

}
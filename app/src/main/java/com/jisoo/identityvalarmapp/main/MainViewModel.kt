package com.jisoo.identityvalarmapp.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.model.AlarmRepository
import com.jisoo.identityvalarmapp.model.CharacInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: AlarmRepository = AlarmRepository(application)

    private val _characList = MutableLiveData<List<CharacInfo>>()
    val characList: LiveData<List<CharacInfo>> = _characList

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

    private val _onVolumeEditClicked = MutableLiveData<Boolean>()
    val onVolumeEditClicked: LiveData<Boolean> = _onVolumeEditClicked

    private val _onNotiCloseBtnClicked = MutableLiveData<Boolean>()
    val onNotiCloseBtnClicked: LiveData<Boolean> = _onNotiCloseBtnClicked

    private val _onNotiConfirmBtnClicked = MutableLiveData<Boolean>()
    val onNotiConfirmBtnClicked: LiveData<Boolean> = _onNotiConfirmBtnClicked

    private val _onCloseBtnClicked = MutableLiveData<Boolean>()
    val onCloseBtnClicked: LiveData<Boolean> = _onCloseBtnClicked

    private val _onConfirmBtnClicked = MutableLiveData<Boolean>()
    val onConfirmBtnClicked: LiveData<Boolean> = _onConfirmBtnClicked

    private val _onWarningCloseBtnClicked = MutableLiveData<Boolean>()
    val onWarningClostBtnCLicked: LiveData<Boolean> = _onWarningCloseBtnClicked

    private val _onNeedUpdateConfirmBtnClicked = MutableLiveData<Boolean>()
    val onNeedUpdateConfirmBtnClicked: LiveData<Boolean> = _onNeedUpdateConfirmBtnClicked

    private val _onLicenseBtnClicked = MutableLiveData<Boolean>()
    val onLicenseBtnClicked: LiveData<Boolean> = _onLicenseBtnClicked

    private val _onSeekbarStatusListener = MutableLiveData<Int>()
    val onSeekbarStatusListener: LiveData<Int> = _onSeekbarStatusListener

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

        viewModelScope.launch(Dispatchers.IO) {
            _characList.postValue(repository.getAllData())
        }

        _reviewClicked.value = false
        _feedbackClicked.value = false
        _onTimeEditClicked.value = false
        _onVolumeEditClicked.value = false
        _onNotiCloseBtnClicked.value = false
        _onNotiConfirmBtnClicked.value = false
        _onCloseBtnClicked.value = false
        _onWarningCloseBtnClicked.value = false
        _onNeedUpdateConfirmBtnClicked.value = false
        _onConfirmBtnClicked.value = false
        _onLicenseBtnClicked.value = false
        _timeSet.value = false
        _finishFlag.value = false
        _toastFlag.value = false
        _appVer.value =
            application.packageManager.getPackageInfo(application.packageName, 0).versionName

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

    fun writeAReview() {
        _reviewClicked.value = true
    }

    fun feedbackView() {
        _feedbackClicked.value = true
    }

    fun onAlarmTimeEditClicked() {
        _onTimeEditClicked.value = true
    }

    fun onNotiVolumeEditClicked() {
        _onVolumeEditClicked.value = true
    }

    fun onNotiDialogCloseBtnClicked() {
        _onNotiCloseBtnClicked.value = true
    }

    fun onNotiDialogConfirmBtnClicked() {
        _onNotiConfirmBtnClicked.value = true
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

    fun onLicenseBtnClicked() {
        _onLicenseBtnClicked.value = true
    }

    fun setTime(hour: String?, minute: String?) {
        _hour.value = hour!!
        _minute.value = minute!!
        _time.value = hour + minute
    }

    fun onSeekbarListener(status: Int) {
        _onSeekbarStatusListener.value = status
    }

    fun onBackPressedMethod() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            _toastFlag.value = true
            _finishFlag.value = false
            return
        }
        if (System.currentTimeMillis() <= backPressedTime + 2000) {
            _finishFlag.value = true
            _toastFlag.value = false
        }
    }

}
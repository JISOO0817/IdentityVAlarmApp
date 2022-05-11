package com.jisoo.identityvalarmapp.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jisoo.identityvalarmapp.model.AlarmRepository
import com.jisoo.identityvalarmapp.model.CharacInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application){

    private var repository : AlarmRepository = AlarmRepository(application)
    var surList : LiveData<List<CharacInfo>> = repository.surList
    var hunList: LiveData<List<CharacInfo>> = repository.hunList

    private val _checkClicked = MutableLiveData<Boolean>()
    val checkClicked: LiveData<Boolean>
        get() = _checkClicked

    fun update(info: CharacInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(info)
        }
    }

    init {
        _checkClicked.value = false
    }

    fun onCheckBoxClicked() {
        _checkClicked.value = true
    }

    override fun onCleared() {
        super.onCleared()
    }

}
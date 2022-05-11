package com.jisoo.identityvalarmapp.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.ActivityMainBinding
import com.jisoo.identityvalarmapp.databinding.DialogPopupBinding
import com.jisoo.identityvalarmapp.util.dialog.DialogSize
import com.jisoo.identityvalarmapp.util.dialog.PopupDialog
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var popupBinding: DialogPopupBinding
    private lateinit var popupDialog: PopupDialog

    private lateinit var sharedPref : SharedPreferences

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initDialog()
        initObserver()
        checkExplPopup()
    }

    fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        popupBinding = DataBindingUtil.inflate(LayoutInflater.from(applicationContext), R.layout.dialog_popup, null, false)
        popupBinding.viewModel = viewModel
        popupBinding.lifecycleOwner = this

        binding.viewPager.adapter = FragmentPagerAdapter(this)

        val tabTextList = listOf("생존자","감시자")

        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    fun initDialog() {
        popupDialog = PopupDialog(this,popupBinding)

    }

    @SuppressLint("SimpleDateFormat")
    fun checkExplPopup() {

        val stPreferences = "Day"
        val strDate = "0"

        val currentTime = System.currentTimeMillis()
        val todayDate = Date(currentTime)
        val sdFormat = SimpleDateFormat("dd")
        val strSDFormatDay = sdFormat.format(todayDate)

        stopWatchingTodayState(stPreferences,strDate,strSDFormatDay)
    }

    @SuppressLint("SimpleDateFormat")
    fun stopWatchingTodayState(stPreferences: String, strDate: String, strSDFormatDay: String,) {

        sharedPref = getSharedPreferences("todayPopup", Context.MODE_PRIVATE)
        val strPreferencesDay = sharedPref.getString(stPreferences,strDate)

        if(strSDFormatDay.toInt() - strPreferencesDay!!.toInt() != 0) {
            Log.d("jjs","strSDFormatDay:${strSDFormatDay}"+"strPreferencesDay:${strPreferencesDay}")
            showPopupDialog()
        }

    }

    fun initObserver() {
        viewModel.checkClicked.observe(this, {
            if(it == true) {

                val currentTime = System.currentTimeMillis()
                val todayDate = Date(currentTime)
                val sdFormat = SimpleDateFormat("dd")
                val strSDFormatDay = sdFormat.format(todayDate)
                //sharedpreference 에 저장 (오늘하루안보기)
                sharedPref.edit().run {
                    putString("Day",strSDFormatDay)
                    commit()
                }
                popupDialog.dismiss()
            }
        })
    }

    private fun showPopupDialog() {
        popupDialog.show()
        DialogSize.initDialogLayout(popupDialog,this)
    }
}
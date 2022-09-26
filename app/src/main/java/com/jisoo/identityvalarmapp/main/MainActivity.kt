package com.jisoo.identityvalarmapp.main

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.databinding.ActivityMainBinding
import com.jisoo.identityvalarmapp.databinding.DialogNeedUpdateBinding
import com.jisoo.identityvalarmapp.model.AlarmRunFunction
import com.jisoo.identityvalarmapp.util.Const.Companion.DEFAULT_TIME
import com.jisoo.identityvalarmapp.util.Const.Companion.FIREBASE_VERSION
import com.jisoo.identityvalarmapp.util.Const.Companion.LANGUAGE_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.MODE_EN
import com.jisoo.identityvalarmapp.util.Const.Companion.MODE_JA
import com.jisoo.identityvalarmapp.util.Const.Companion.MODE_KO
import com.jisoo.identityvalarmapp.util.Const.Companion.TIME_SP
import com.jisoo.identityvalarmapp.util.dialog.DialogSize
import com.jisoo.identityvalarmapp.util.dialog.Margin
import com.jisoo.identityvalarmapp.util.dialog.NeedUpdateDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.*
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var needUpdateBinding: DialogNeedUpdateBinding
    private lateinit var needUpdateDialog: NeedUpdateDialog
    private val viewModel: MainViewModel by viewModels()
    private lateinit var configuration: Configuration
    private lateinit var runFunc: AlarmRunFunction
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.loadingTheme)
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_IdentityVAlarmApp)

//        val fcm = Intent(applicationContext, FCMService::class.java)
//        startService(fcm)

        setUpBinding()
        setUpView()
        setUpObserver()
        checkLanguageStatus()
//        setUpTokenOnServer()
        checkPreferencesTime()
        getFirebaseAppVersion()

    }

    fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewPager.adapter = FragmentPagerAdapter(this)

        runFunc = AlarmRunFunction(this)

        needUpdateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.dialog_need_update,
            null,
            false
        )

        needUpdateDialog = NeedUpdateDialog(this, needUpdateBinding, this.viewModel)
        needUpdateBinding.viewModel = viewModel
        needUpdateBinding.lifecycleOwner = this

    }

    private fun setUpView() {
        val tabTextList = listOf(
            resources.getString(R.string.main_activity_first_tab),
            resources.getString(R.string.main_activity_second_tab)
        )

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        for (i in 0..2) {
            val textView = LayoutInflater.from(this).inflate(R.layout.tab_title, null) as TextView
            binding.tabs.getTabAt(i)?.customView = textView
            textView.textSize = getConvertDpByRes(15f)
        }
    }

    private fun setUpObserver() {
        viewModel.characList.observe(this) {
            Log.d("tett", "main activity list size:${it.size}")
            if (it.isNotEmpty()) {
                runFunc.checkAlarm(it)
            }
        }

        viewModel.onNeedUpdateConfirmBtnClicked.observe(this) {
            if (it == true) {
                goToUpdate()
                dismissUpdateDialog()
            }
        }

        viewModel.toastFlag.observe(this) {
            if (it == true) {
                showFinishToast()
            }
        }

        viewModel.finishFlag.observe(this) {
            if (it == true) {
                finish()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(!hasFocus) {
            Log.d("screen","true")

        } else {

        }
    }

    private fun checkLanguageStatus() {
        Log.d("language", "checkLanguageStatus 호출")
        configuration = Configuration(this.resources.configuration)
        if (TextUtils.equals(App.prefs.getLanguage(LANGUAGE_SP, ""), MODE_KO)) {
            configuration.setLocale(Locale.KOREAN)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else if (TextUtils.equals(App.prefs.getLanguage(LANGUAGE_SP, ""), MODE_EN)) {
            configuration.setLocale(Locale.US)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else if (TextUtils.equals(App.prefs.getLanguage(LANGUAGE_SP, ""), MODE_JA)) {
            configuration.setLocale(Locale.JAPANESE)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else {
            val defaultLanguage = Locale.getDefault().language
            App.prefs.setLanguage(LANGUAGE_SP, defaultLanguage)
        }

        val refreshFlag = intent.extras?.get("refresh")
        if (refreshFlag != null && refreshFlag as Boolean) {
            return
        }
        refreshApp()
    }

    private fun refreshApp() {
        Log.d("refresh", "refreshApp 호출")
        val intent = Intent(this.packageManager.getLaunchIntentForPackage(this.packageName))
        intent.flags.apply {
            Intent.FLAG_ACTIVITY_NEW_TASK
            Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        intent.putExtra("refresh", true)
        this.finish()
        startActivity(intent)
    }

//    private fun setUpTokenOnServer() {
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
////                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//        })
//    }

    override fun onResume() {
        super.onResume()
        getFirebaseAppVersion()
    }

    override fun onPause() {
        super.onPause()
//        Log.d("eeeeee", "onPause")
//        setTheme(R.style.loadingTheme)
    }

    override fun onStop() {
        super.onStop()
        Log.d("eeeeee", "onStop")
    }

    private fun checkPreferencesTime() {
        if (TextUtils.equals("", App.prefs.getTime(TIME_SP, ""))) {
            App.prefs.setTime(TIME_SP, DEFAULT_TIME)
            viewModel.setTime("13", "00")
        }
    }

    /**
     * firebase remote config 버전 가져오기기
     * **/
    private fun getFirebaseAppVersion() {

        Log.d("version", "getFirebaseAppVersion 호출")
        val myAppVersion = packageManager.getPackageInfo(packageName, 0).versionName

        lifecycleScope.launch(Dispatchers.Default) {
            val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
            firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_default)
                .addOnCompleteListener { task ->
                    firebaseRemoteConfig.fetchAndActivate()
                    if (task.isSuccessful) {
                        val firebase = firebaseRemoteConfig.getString(FIREBASE_VERSION)
                        if (checkNeedUpdate(myAppVersion, firebase)) {
                            showNeedUpdateDialog()
                        }
                    } else {
                        Log.d("version", "fail")
                    }
                }
        }

    }

    /**
     * 버전비교
     * **/
    private fun checkNeedUpdate(currVersion: String, newVersion: String): Boolean {
        val currVersionSplit: Array<String> = currVersion.split(".").toTypedArray()
        val newVersionSplit: Array<String> = newVersion.split(".").toTypedArray()

        val currMajorVer = currVersionSplit[0].toInt()
        val currMinorVer = currVersionSplit[1].toInt()
        val currPointVer = currVersionSplit[2].toInt()
        val newMajorVer = newVersionSplit[0].toInt()
        val newMinorVer = newVersionSplit[1].toInt()
        val newPointVer = newVersionSplit[2].toInt()

        var isNeededTobeUpdate = false
        if (currMajorVer < newMajorVer) isNeededTobeUpdate = true
        if (currMajorVer == newMajorVer && currMinorVer < newMinorVer) isNeededTobeUpdate = true
        if (currMajorVer == newMajorVer && currMinorVer == newMinorVer && currPointVer < newPointVer) isNeededTobeUpdate =
            true

        return isNeededTobeUpdate
    }

    private fun showNeedUpdateDialog() {
        needUpdateDialog.show()
        DialogSize.initDialogLayout(needUpdateDialog, this)

        needUpdateDialog.setFontSize(16f, 14f, 14f)

        needUpdateDialog.setMargin(
            setTypedValue(16f, 0f, 16f, 16f),
            setTypedValue(12f, 0f, 0f, 0f)
        )

        needUpdateDialog.setBtnSize(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(44f).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt()
        )
    }

    private fun goToUpdate() {
        val url = "market://details?id=" + "com.jisoo.identityvalarmapp"
        val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        playIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(playIntent)
    }


    private fun dismissUpdateDialog() {
        if (needUpdateDialog.isShowing) {
            needUpdateDialog.dismiss()
        }
    }

    private fun setTypedValue(
        topMargin: Float,
        bottomMargin: Float,
        leftMargin: Float,
        rightMargin: Float
    ): Margin {
        return Margin(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(topMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(bottomMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(leftMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(rightMargin).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt()
        )
    }

    private fun getConvertDpByRes(dpSize: Float): Float {
        val weight: Float
        val dm = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val wi = width.toDouble() / dm.xdpi.toDouble()
        weight = (wi / 2.86817851).toFloat()
        return dpSize * weight
    }

    private fun showFinishToast() {
        Toast.makeText(this, R.string.main_activity_finish_txt, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
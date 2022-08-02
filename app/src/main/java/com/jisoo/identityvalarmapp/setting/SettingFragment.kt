package com.jisoo.identityvalarmapp.setting

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jisoo.identityvalarmapp.BuildConfig
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.databinding.*
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.model.AlarmRunFunction
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.Const.Companion.SWITCH_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.TIME_SP
import com.jisoo.identityvalarmapp.util.dialog.*
import java.lang.Exception
import kotlin.math.roundToInt

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    /**
     * 액티비티와 뷰모델을 공유
     * */
    private val model: MainViewModel by activityViewModels()

    private lateinit var timeEditBinding: DialogTimeeditBinding
    private lateinit var timeEditDialog: TimeEditDialog

    private lateinit var notiVolumeBinding: DialogNotiVolumeBinding
    private lateinit var notiVolumeDialog: NotiVolumeDialog

    private lateinit var warningBinding: DialogOnoffWarningBinding
    private lateinit var warningDialog: OnOffWarningDialog

    private lateinit var licenseBinding: DialogLicenseBinding
    private lateinit var licenseDialog: LicenseDialog

    private lateinit var runFunc: AlarmRunFunction

    var hour: Int? = null
    var minute: Int? = null

    private var seekBarStatus: Int? = null

    private lateinit var solarList: List<CharacInfo>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        timeEditBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_timeedit, container, false
        )

        warningBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_onoff_warning, container, false
        )

        licenseBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_license, container, false
        )

        notiVolumeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_noti_volume, container, false
        )


        setUpBinding()
        setUpView()
        setUpObserver()
        runFunc = AlarmRunFunction(requireActivity())

        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = model
        binding.lifecycleOwner = viewLifecycleOwner

        binding.onoffView.bind.viewModel = model
        binding.onoffView.bind.lifecycleOwner = viewLifecycleOwner

        timeEditDialog = TimeEditDialog(requireActivity(), timeEditBinding)
        timeEditBinding.viewmodel = model
        timeEditBinding.lifecycleOwner = viewLifecycleOwner

        warningDialog = OnOffWarningDialog(requireActivity(), warningBinding)
        warningBinding.viewModel = model
        warningBinding.lifecycleOwner = viewLifecycleOwner

        licenseDialog = LicenseDialog(requireActivity(), licenseBinding)
        licenseBinding.lifecycleOwner = viewLifecycleOwner

        notiVolumeDialog = NotiVolumeDialog(requireActivity(), notiVolumeBinding,model)
        notiVolumeBinding.viewModel = model
        notiVolumeBinding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setUpView() {
        binding.onoffView.bind.onoffSwitch.isChecked = App.prefs.checkPreferencesStatus()
        binding.onoffView.bind.switchStatus.text = model.initSwitchStatusText().toString()
        binding.alarmClockEditView.bind.subTv.text = App.prefs.getTime("time", "")

        notiVolumeDialog.setSeekbarViewState()
    }

    private fun setUpObserver() {
        model.characList.observe(viewLifecycleOwner, {
            solarList = it
        })

        model.time.observe(viewLifecycleOwner, {
            binding.alarmClockEditView.bind.subTv.text = setTimeValue(model.hour.value!!.toInt(),model.minute.value!!.toInt())
        })

        model.switchClicked.observe(viewLifecycleOwner, {
            App.prefs.setBoolean(SWITCH_SP, binding.onoffView.bind.onoffSwitch.isChecked)
            if (it == true) {
                turnOnTheAlarm()
            } else {
                turnOffTheAlarm()
            }
        })

        model.onTimeEditClicked.observe(viewLifecycleOwner, {
            if (it == true) {
                checkOnOffStatus()
            }
        })

        model.onSeekbarStatusListener.observe(viewLifecycleOwner, {
            seekBarStatus = it
        })

        model.onVolumeEditClicked.observe(viewLifecycleOwner, {
            if (it == true) {
                showVolumeEditDialog()
            }
        })

        model.onNotiCloseBtnClicked.observe(viewLifecycleOwner, {
            if ( it == true) {
                dismissNotiVolumeDialog()
            }
        })

        model.onNotiConfirmBtnClicked.observe(viewLifecycleOwner, {
            if (it  == true) {
                storeValueInSP(seekBarStatus)
            }
        })

        model.reviewClicked.observe(viewLifecycleOwner, {
            Log.d("review","review 옵저버 호출")
            if (it == true) {
                goPlayStore()
            }
        })

        model.feedbackClicked.observe(viewLifecycleOwner, {
            if (it == true) {
                sendEmail()
            }
        })

        model.onCloseBtnClicked.observe(viewLifecycleOwner, {
            if (it == true) {
                dismissTimeEditDialog()
            }
        })

        model.onConfirmBtnClicked.observe(viewLifecycleOwner, {
            if (it == true) {
                updateDBTimeInfo()
                updateAlarmManager()
            }
        })

        model.timeSet.observe(viewLifecycleOwner, {
            if (it == true) {
                binding.alarmClockEditView.bind.subTv.text = model.hour.value
            }
        })

        model.onWarningClostBtnCLicked.observe(viewLifecycleOwner, {
            if (it == true) {
                dismissWarningDialog()
            }
        })

        model.onLicenseBtnClicked.observe(viewLifecycleOwner, {
            if (it == true) {
                showLicenseDialog()
            }
        })
    }

    //TODO: 테스트 필요
    private fun sendEmail() {
        val address = R.string.fragment_setting_email_add_txt
        val app_version = BuildConfig.VERSION_NAME
        val device = Build.MODEL
        val sdk = Build.VERSION.RELEASE

        val email = Intent(Intent.ACTION_SEND)
        email.type = "message/rfc822"
        email.putExtra(Intent.EXTRA_EMAIL, address)
        email.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.fragment_setting_email_feedback_info_txt))
        email.putExtra(Intent.EXTRA_TEXT,
            "App Version:${app_version},\nDevice:${device},\nAndroid(SDK):${sdk}")
        requireActivity().startActivity(email)
    }

    private fun turnOffTheAlarm() {
        val nearList = runFunc.getClossetCharacList(solarList)

        for(i in nearList.indices) {
            runFunc.removeAlarmManager(nearList[i].uid.toInt())
            Log.d("tttttt","꺼진알람 :${nearList[i].job}")
        }

    }

    private fun turnOnTheAlarm() {
        runFunc.checkAlarm(solarList)
    }

    private fun checkOnOffStatus() {
        if (App.prefs.checkPreferencesStatus()) {
            showTimeEditDialog()
        } else {
            showCautionDialog()
        }
    }

    private fun updateAlarmManager() {
        Log.d("toggle", "updateAlarmManager 호출")
        runFunc.checkAlarm(solarList)
    }

    private fun updateDBTimeInfo() {
        hour = TimePickerUtil.getHour(timeEditBinding.timePicker)
        minute = TimePickerUtil.getMinute(timeEditBinding.timePicker)

        App.prefs.setTime(TIME_SP, setTimeValue(hour!!, minute!!))
        model.setTime(hour.toString(), minute.toString())

        timeEditDialog.dismiss()
    }

    private fun setTimeValue(hour: Int, minute: Int): String {
        if (minute.toString() == "0") {
            return  hour.toString() + " : " + minute.toString() + "0"
        } else if (minute < 10) {
            return "$hour: 0$minute"
        }

        return "$hour : $minute"
    }

    private fun dismissTimeEditDialog() {
        if (timeEditDialog.isShowing) {
            timeEditDialog.dismiss()
        }
    }

    private fun dismissWarningDialog() {
        if (warningDialog.isShowing) {
            warningDialog.dismiss()
        }
    }

    private fun showTimeEditDialog() {
        timeEditDialog.show()
        DialogSize.initDialogLayout(timeEditDialog, requireActivity())

        timeEditDialog.setFontSize(16f, 14f, 14f, 14f)

        timeEditDialog.setMargin(
            setTypedValue(16f, 0f, 16f, 16f),
            setTypedValue(12f, 0f, 0f, 0f)
        )

        timeEditDialog.setBtnSize(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(44f).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt()
        )
    }

    /**
     * sharedpreference 에 int (알람중요도) 값을 저장함.
     * 알람노티를 띄울 때 저장된 sharedpreference 값을 가져와 참고하여 울리게함
     * **/
    
    private fun storeValueInSP(it: Int?) {
        if (it != null) {
            App.prefs.setAlarmImportance("alarm",it)
        }
        
        if(notiVolumeDialog.isShowing) {
            notiVolumeDialog.dismiss()
        }
    }

    private fun showVolumeEditDialog() {
        notiVolumeDialog.show()
        DialogSize.initDialogLayout(notiVolumeDialog,requireActivity())

        notiVolumeDialog.setFontSize(16f,14f,14f)
        notiVolumeDialog.setMargin(
            setTypedValue(16f,0f,16f,16f),
            setTypedValue(12f,0f,0f,0f)
        )
        notiVolumeDialog.setBtnSize(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(44f).roundToInt().toFloat()
                ,resources.displayMetrics
            ).toInt()
        )

    }

    private fun dismissNotiVolumeDialog() {
        if (notiVolumeDialog.isShowing) {
            notiVolumeDialog.dismiss()
        }
    }

    private fun showCautionDialog() {
        warningDialog.show()
        DialogSize.initDialogLayout(warningDialog, requireActivity())
        warningDialog.setFontSize(16f, 14f, 14f)

        warningDialog.setMargin(
            setTypedValue(16f, 0f, 16f, 16f),
            setTypedValue(12f, 0f, 0f, 0f)
        )

        warningDialog.setBtnSize(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getConvertDpByRes(44f).roundToInt().toFloat(),
                resources.displayMetrics
            ).toInt()
        )

    }

    private fun goPlayStore() {
        val url = resources.getString(R.string.identityv_MarketName)+resources.getString(R.string.app_channelId)
        try {
            val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            playIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(playIntent)
        }catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = (Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.app_channelId)))
            if (webIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(webIntent)
            }
        }
    }

    private fun showLicenseDialog() {
        licenseDialog.show()
        DialogSize.initDialogLayout(licenseDialog, requireActivity())
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
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val wi = width.toDouble() / dm.xdpi.toDouble()
        weight = (wi / 2.86817851).toFloat()
        return dpSize * weight
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }


}
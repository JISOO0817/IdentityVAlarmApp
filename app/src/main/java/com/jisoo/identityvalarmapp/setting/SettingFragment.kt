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
import com.jisoo.identityvalarmapp.databinding.DialogOnoffWarningBinding
import com.jisoo.identityvalarmapp.databinding.DialogTimeeditBinding
import com.jisoo.identityvalarmapp.databinding.FragmentSettingBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.model.CharacInfo
import com.jisoo.identityvalarmapp.util.Const.Companion.SWITCH_SP
import com.jisoo.identityvalarmapp.util.Const.Companion.TIME_SP
import com.jisoo.identityvalarmapp.util.dialog.DialogSize
import com.jisoo.identityvalarmapp.util.dialog.Margin
import com.jisoo.identityvalarmapp.util.dialog.OnOffWarningDialog
import com.jisoo.identityvalarmapp.util.dialog.TimeEditDialog
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

    private lateinit var warningBinding: DialogOnoffWarningBinding
    private lateinit var warningDialog: OnOffWarningDialog

    var hour: Int? = null
    var minute: Int? = null

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

        setUpBinding()
        setUpView()
        setUpObserver()

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

    }

    private fun setUpView() {
        binding.onoffView.bind.onoffSwitch.isChecked = App.prefs.checkPreferencesStatus()
        binding.onoffView.bind.switchStatus.text = model.initSwitchStatusText().toString()
        binding.alarmClockEditView.bind.subTv.text = App.prefs.getTime("time", "")
    }

    private fun setUpObserver() {
        model.sortedSolarList.observe(viewLifecycleOwner, {
            solarList = it
            Log.d("test","solarList:${solarList}")
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
                dismissDialog()
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
        val nearList: List<CharacInfo> = model.getClossetCharacList(solarList)
        for (charac in nearList) {
            charac.removeAlarmManager(requireActivity(), removeUid = charac.uid.toInt())
            Log.d("toggle", "tunOffTheAlarm 삭제된 알람매니저 정보 :${charac.job}, ${charac.uid}")
        }
    }

    private fun turnOnTheAlarm() {
        model.checkAlarm(requireActivity(), solarList)
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
        model.checkAlarm(requireActivity(), solarList)
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

    private fun dismissDialog() {
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
        val url = resources.getString(R.string.identityv_MarketName)+resources.getString(R.string.identityv_Name)
        try {
            val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            playIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(playIntent)
        }catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW)
            webIntent.data = (Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.identityv_Name)))
            if (webIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(webIntent)
            }
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
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(dm)
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
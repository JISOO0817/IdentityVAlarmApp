package com.jisoo.identityvalarmapp.util.customview

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.databinding.CustomOnoffViewBinding
import com.jisoo.identityvalarmapp.databinding.CustomOnoffViewBindingImpl

class CustomOnOffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet
) : ConstraintLayout(context, attrs){

    lateinit var bind: CustomOnoffViewBinding

    init {
        init(context)
    }

    private fun init(context: Context) {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_onoff_view,
            this,
            true
        )

        bind.alarmTv.textSize = getConvertDpByRes(13f)
        bind.switchStatus.textSize = getConvertDpByRes(13f)
    }

    private fun getConvertDpByRes(dpSize: Float): Float {
        val manager = context.getSystemService(WindowManager::class.java)
        val weight: Float
        val dm = DisplayMetrics()
        manager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val wi = width.toDouble() / dm.xdpi.toDouble()
        weight = (wi / 2.86817851).toFloat()
        return dpSize * weight
    }
}
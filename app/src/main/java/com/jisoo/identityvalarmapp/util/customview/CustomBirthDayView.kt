package com.jisoo.identityvalarmapp.util.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.CustomBirthdayViewBinding
import android.view.WindowManager


class CustomBirthDayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    lateinit var bind: CustomBirthdayViewBinding

    init {
        init(context, attrs)
    }

    @SuppressLint("Recycle")
    fun init(context: Context, attrs: AttributeSet) {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_birthday_view,
            this,
            true
        )

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomBirthDayView,
            0, 0
        ).apply {
            try {
                val typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.CustomBirthDayView)
                val title = typedArray.getString(R.styleable.CustomBirthDayView_monthText)
                title?.let {
                    setMonthTitle(it)
                }

            } finally {
                recycle()
            }
        }

        bind.monthTv.textSize = getConvertDpByRes(15f)

    }

    fun setMonthTitle(text: String) {
        bind.monthTv.text = text
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


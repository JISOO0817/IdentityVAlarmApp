package com.jisoo.identityvalarmapp.util.customview

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.CustomTitleviewBinding

class CustomTitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet
) : ConstraintLayout(context, attrs) {

    lateinit var bind: CustomTitleviewBinding

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_titleview,
            this,
            true
        )
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTitleView,
            0, 0
        ).apply {
            try {
                val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView)
                val title = typedArray.getString(R.styleable.CustomTitleView_titleName)

                title?.let {
                    setTitle(it)
                }

            } finally {
                recycle()
            }
        }

        bind.titleTv.textSize = getConvertDpByRes(15f)

    }

    fun setTitle(text: String) {
        bind.titleTv.text = text
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
package com.jisoo.identityvalarmapp.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.CustomBirthdayViewBinding

class CustomBirthDayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet) : ConstraintLayout(context,attrs) {

    lateinit var bind: CustomBirthdayViewBinding

        init {
            init(context, attrs)
        }

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
            0,0
        ).apply {
            try {
                val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBirthDayView)
                val title = typedArray.getString(R.styleable.CustomBirthDayView_monthText)
//                val subText = typedArray.getString(R.styleable.CustomSubTitleView_subText)
//                val exist = typedArray.getInteger(R.styleable.CustomSubTitleView_subExist,0)

                title?.let {
                    setMonthTitle(it)
                }

//                subText?.let {
//                    setSubText(it)
//                }
//
//                when(exist) {
//                    0 -> bind.subTv.visibility = View.GONE
//                    1-> bind.subTv.visibility = View.VISIBLE
//                }

            } finally {
                recycle()
            }
        }


    }


    fun setMonthTitle(text: String) {
        bind.monthTv.text = text
    }
}
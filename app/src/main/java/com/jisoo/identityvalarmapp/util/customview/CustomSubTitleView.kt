package com.jisoo.identityvalarmapp.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.CustomSubtitleViewBinding

class CustomSubTitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs){

    lateinit var bind: CustomSubtitleViewBinding

        init {
            init(context,attrs)
        }

    private fun init(context: Context, attrs: AttributeSet) {
        bind = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_subtitle_view,
            this,
            true
        )

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomSubTitleView,
            0,0
        ).apply {
            try {
                val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSubTitleView)
                val title = typedArray.getString(R.styleable.CustomSubTitleView_subTitleName)
                val subText = typedArray.getString(R.styleable.CustomSubTitleView_subText)
                val exist = typedArray.getInteger(R.styleable.CustomSubTitleView_subExist,0)

                title?.let {
                    setTitle(it)
                }

                subText?.let {
                    setSubText(it)
                }

                when(exist) {
                    0 -> bind.subTv.visibility = View.GONE
                    1-> bind.subTv.visibility = View.VISIBLE
                }



            } finally {
                recycle()
            }
        }

    }

    fun setTitle(text: String) {
        bind.subtitleTv.text = text
    }

    fun setSubText(text: String) {
        bind.subTv.text = text
    }


}
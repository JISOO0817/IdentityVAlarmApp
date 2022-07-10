package com.jisoo.identityvalarmapp.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
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

    }

    fun setTitle(text: String) {
        bind.titleTv.text = text
    }


}
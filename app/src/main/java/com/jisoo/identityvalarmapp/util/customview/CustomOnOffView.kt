package com.jisoo.identityvalarmapp.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
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

    }
}
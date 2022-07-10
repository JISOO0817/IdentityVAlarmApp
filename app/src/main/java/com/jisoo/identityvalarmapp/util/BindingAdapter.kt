package com.jisoo.identityvalarmapp.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.jisoo.identityvalarmapp.generated.callback.OnClickListener
import com.jisoo.identityvalarmapp.util.customview.CustomOnOffView

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("android:on_switch_clicked")
    fun setOnSwitchClicked(view: CustomOnOffView, onClickListener: View.OnClickListener) {
        view.bind.onoffSwitch.setOnClickListener(onClickListener)
    }
}
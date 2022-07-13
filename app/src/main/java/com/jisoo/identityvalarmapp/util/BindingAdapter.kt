package com.jisoo.identityvalarmapp.util

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jisoo.identityvalarmapp.generated.callback.OnClickListener
import com.jisoo.identityvalarmapp.util.customview.CustomOnOffView

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("android:on_switch_clicked")
    fun setOnSwitchClicked(view: CustomOnOffView, onClickListener: View.OnClickListener) {
        view.bind.onoffSwitch.setOnClickListener(onClickListener)
    }

    @JvmStatic
    @BindingAdapter("android:license_clicked")
    fun setOnLicenseClicked(textView: TextView, link: String?) {
        textView.setOnClickListener {
            val uri = Uri.parse(link)
            val context = textView.context
            val webIntent = Intent(Intent.ACTION_VIEW, uri)
            webIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(webIntent)
        }
    }
}
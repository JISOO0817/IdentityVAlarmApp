package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.jisoo.identityvalarmapp.databinding.DialogOnoffWarningBinding

class OnOffWarningDialog(context: Context, binding: DialogOnoffWarningBinding): Dialog(context) {

    private var onoffWarningBinding: DialogOnoffWarningBinding

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        onoffWarningBinding = binding
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        setContentView(binding.root)
    }

    fun setFontSize(titleFontSize: Float, infoFontSize: Float, closeFontSize: Float) {
        onoffWarningBinding.warningTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,titleFontSize)
        onoffWarningBinding.warningSubtitleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,infoFontSize)
        onoffWarningBinding.closeBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,closeFontSize)
    }

    fun setMargin(titleMargin: Margin, subtitleMargin: Margin) {
        val titleLayoutParams = onoffWarningBinding.warningTitleTv.layoutParams as ConstraintLayout.LayoutParams
        titleLayoutParams.topMargin = titleMargin.topMargin
        titleLayoutParams.leftMargin = titleMargin.leftMargin
        titleLayoutParams.rightMargin = titleMargin.rightMargin
        onoffWarningBinding.warningTitleTv.layoutParams = titleLayoutParams
        onoffWarningBinding.warningTitleTv.requestLayout()

        val infoLayoutParams = onoffWarningBinding.warningSubtitleTv.layoutParams as ConstraintLayout.LayoutParams
        infoLayoutParams.topMargin = subtitleMargin.topMargin
        infoLayoutParams.leftMargin = subtitleMargin.leftMargin
        infoLayoutParams.rightMargin = subtitleMargin.rightMargin
        onoffWarningBinding.warningSubtitleTv.layoutParams = infoLayoutParams
        onoffWarningBinding.warningSubtitleTv.requestLayout()
    }

    fun setBtnSize(btnHeight: Int) {
        val closeBtnLayoutParams = onoffWarningBinding.closeBtn.layoutParams as ConstraintLayout.LayoutParams
        closeBtnLayoutParams.height = btnHeight
        onoffWarningBinding.closeBtn.layoutParams = closeBtnLayoutParams
        onoffWarningBinding.closeBtn.requestLayout()
    }
}
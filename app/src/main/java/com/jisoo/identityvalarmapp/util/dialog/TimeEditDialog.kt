package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import com.jisoo.identityvalarmapp.databinding.DialogTimeeditBinding

class TimeEditDialog(context: Context, binding: DialogTimeeditBinding):
    Dialog(context){

    private var timeeditBinding: DialogTimeeditBinding

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        timeeditBinding = binding
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        setContentView(binding.root)
    }

    fun setFontSize(titleFontSize: Float, infoFontSize: Float, closeFontSize: Float, confirmFontSize: Float) {
        timeeditBinding.timeeditTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,titleFontSize)
        timeeditBinding.editInfoTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,infoFontSize)
        timeeditBinding.closeBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,closeFontSize)
        timeeditBinding.confirmBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,confirmFontSize)
    }

    fun setMargin(titleMargin: Margin, infoMargin: Margin) {
        val titleLayoutParams = timeeditBinding.timeeditTitleTv.layoutParams as ConstraintLayout.LayoutParams
        titleLayoutParams.topMargin = titleMargin.topMargin
        titleLayoutParams.leftMargin = titleMargin.leftMargin
        titleLayoutParams.rightMargin = titleMargin.rightMargin
        timeeditBinding.timeeditTitleTv.layoutParams = titleLayoutParams
        timeeditBinding.timeeditTitleTv.requestLayout()

        val infoLayoutParams = timeeditBinding.editInfoTv.layoutParams as ConstraintLayout.LayoutParams
        infoLayoutParams.topMargin = infoMargin.topMargin
        infoLayoutParams.leftMargin = infoMargin.leftMargin
        infoLayoutParams.rightMargin = infoMargin.rightMargin
        timeeditBinding.editInfoTv.layoutParams = infoLayoutParams
        timeeditBinding.editInfoTv.requestLayout()
    }

    fun setBtnSize(btnHeight: Int) {
        val closeBtnLayoutParams = timeeditBinding.closeBtn.layoutParams as ConstraintLayout.LayoutParams
        closeBtnLayoutParams.height = btnHeight
        timeeditBinding.closeBtn.layoutParams = closeBtnLayoutParams
        timeeditBinding.closeBtn.requestLayout()

        val confirmBtnLayoutParams = timeeditBinding.confirmBtn.layoutParams as ConstraintLayout.LayoutParams
        confirmBtnLayoutParams.height = btnHeight
        timeeditBinding.confirmBtn.layoutParams = confirmBtnLayoutParams
        timeeditBinding.confirmBtn.requestLayout()
    }

}
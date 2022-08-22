package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.jisoo.identityvalarmapp.databinding.DialogLanguageEditBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import java.lang.reflect.Type

class LanguageEditDialog(context: Context, binding: DialogLanguageEditBinding,viewModel: MainViewModel) : Dialog(context) {

    private var languageEditBinding: DialogLanguageEditBinding
    private var model : MainViewModel

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        languageEditBinding = binding
        this.model = viewModel
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        setContentView(binding.root)
    }

    fun setFontSize(titleFontSize: Float, contentFontSize: Float, closeFontSize: Float, confirmFontSize: Float) {
        languageEditBinding.languageEditTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,titleFontSize)
        languageEditBinding.koreanRb.setTextSize(TypedValue.COMPLEX_UNIT_DIP,contentFontSize)
        languageEditBinding.englishRb.setTextSize(TypedValue.COMPLEX_UNIT_DIP,contentFontSize)
        languageEditBinding.japaneseRb.setTextSize(TypedValue.COMPLEX_UNIT_DIP,contentFontSize)
        languageEditBinding.closeBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,closeFontSize)
        languageEditBinding.confirmBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,confirmFontSize)
    }

    fun setMargin(titleMargin: Margin) {
        val titleLayoutParams = languageEditBinding.languageEditTv.layoutParams as ConstraintLayout.LayoutParams
        titleLayoutParams.topMargin = titleMargin.topMargin
        titleLayoutParams.leftMargin = titleMargin.leftMargin
        titleLayoutParams.rightMargin = titleMargin.rightMargin
        languageEditBinding.languageEditTv.layoutParams = titleLayoutParams
        languageEditBinding.languageEditTv.requestLayout()
    }

    fun setBtnSize(btnHeight: Int) {
        val closeBtnLayoutParams = languageEditBinding.closeBtn.layoutParams as ConstraintLayout.LayoutParams
        closeBtnLayoutParams.height = btnHeight
        languageEditBinding.closeBtn.layoutParams = closeBtnLayoutParams
        languageEditBinding.closeBtn.requestLayout()

        val confirmBtnLayoutParams = languageEditBinding.confirmBtn.layoutParams as ConstraintLayout.LayoutParams
        confirmBtnLayoutParams.height = btnHeight
        languageEditBinding.confirmBtn.layoutParams = confirmBtnLayoutParams
        languageEditBinding.confirmBtn.requestLayout()
    }


}
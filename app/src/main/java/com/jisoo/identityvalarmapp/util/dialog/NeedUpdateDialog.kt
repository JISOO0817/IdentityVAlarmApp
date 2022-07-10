package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.jisoo.identityvalarmapp.databinding.DialogNeedUpdateBinding
import com.jisoo.identityvalarmapp.main.MainViewModel

class NeedUpdateDialog(context: Context, binding: DialogNeedUpdateBinding, viewModel: MainViewModel): Dialog(context) {

    private var needUpdateBinding: DialogNeedUpdateBinding
    private var model : MainViewModel
    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        needUpdateBinding = binding
        this.model = viewModel
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        setContentView(binding.root)
    }

    fun setFontSize(titleFontSize: Float, infoFontSize: Float, closeFontSize: Float) {
        needUpdateBinding.updateTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,titleFontSize)
        needUpdateBinding.updateSubtitleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,infoFontSize)
        needUpdateBinding.goUpdateBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,closeFontSize)
    }

    fun setMargin(titleMargin: Margin, subtitleMargin: Margin) {
        val titleLayoutParams = needUpdateBinding.updateTitleTv.layoutParams as ConstraintLayout.LayoutParams
        titleLayoutParams.topMargin = titleMargin.topMargin
        titleLayoutParams.leftMargin = titleMargin.leftMargin
        titleLayoutParams.rightMargin = titleMargin.rightMargin
        needUpdateBinding.updateTitleTv.layoutParams = titleLayoutParams
        needUpdateBinding.updateTitleTv.requestLayout()

        val infoLayoutParams = needUpdateBinding.updateSubtitleTv.layoutParams as ConstraintLayout.LayoutParams
        infoLayoutParams.topMargin = subtitleMargin.topMargin
        infoLayoutParams.leftMargin = subtitleMargin.leftMargin
        infoLayoutParams.rightMargin = subtitleMargin.rightMargin
        needUpdateBinding.updateSubtitleTv.layoutParams = infoLayoutParams
        needUpdateBinding.updateSubtitleTv.requestLayout()
    }

    fun setBtnSize(btnHeight: Int) {
        val closeBtnLayoutParams = needUpdateBinding.goUpdateBtn.layoutParams as ConstraintLayout.LayoutParams
        closeBtnLayoutParams.height = btnHeight
        needUpdateBinding.goUpdateBtn.layoutParams = closeBtnLayoutParams
        needUpdateBinding.goUpdateBtn.requestLayout()
    }

    override fun onBackPressed() {
        model.onBackPressedMethod()
    }
}
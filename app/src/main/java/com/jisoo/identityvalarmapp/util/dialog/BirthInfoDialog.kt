package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.jisoo.identityvalarmapp.databinding.DialogBirthinfoBinding

class BirthInfoDialog(context: Context, binding: DialogBirthinfoBinding, ok: View.OnClickListener) : Dialog(context){

    private var birthinfoBinding: DialogBirthinfoBinding
    private var ok: View.OnClickListener ? = null

    init {
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        birthinfoBinding = binding
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        this.ok = ok
        binding.closeIv.setOnClickListener(ok)

        setContentView(binding.root)
    }


    fun setFontSize(jobSize: Float ,birthSize: Float) {
        birthinfoBinding.jobTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, jobSize)
        birthinfoBinding.birthTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, birthSize)
    }

    fun setMargin(closeMargin: Margin, jobMargin: Margin, birthMargin: Margin) {
        val closeLayoutParams = birthinfoBinding.closeIv.layoutParams as ConstraintLayout.LayoutParams
        closeLayoutParams.topMargin = closeMargin.topMargin
        closeLayoutParams.rightMargin = closeMargin.rightMargin

        val jobLayoutParams =  birthinfoBinding.jobTv.layoutParams as ConstraintLayout.LayoutParams
        jobLayoutParams.topMargin = jobMargin.topMargin

        val birthLayoutParams = birthinfoBinding.birthTv.layoutParams as ConstraintLayout.LayoutParams
        birthLayoutParams.topMargin = birthMargin.topMargin
        birthLayoutParams.bottomMargin = birthMargin.bottomMargin
    }

}
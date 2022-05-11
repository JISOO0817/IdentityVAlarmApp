package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import com.jisoo.identityvalarmapp.databinding.DialogPopupBinding

class PopupDialog(context: Context, binding: DialogPopupBinding)
    : Dialog(context), View.OnClickListener {

        private var popupBinding: DialogPopupBinding
        private lateinit var okBtn : View.OnClickListener

        init {
            setCanceledOnTouchOutside(false)
            setCancelable(true)
            popupBinding = binding
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            if(binding.root.parent != null) {
                (binding.root.parent as ViewGroup).removeView(binding.root)
            }

            setContentView(binding.root)
        }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

}
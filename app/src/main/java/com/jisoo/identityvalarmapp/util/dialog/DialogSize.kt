package com.jisoo.identityvalarmapp.util.dialog

import android.content.Context
import android.view.WindowManager

object DialogSize {

    fun initDialogLayout(dialog: PopupDialog, context: Context) {
        val dm = context.resources.displayMetrics
        val width = dm.widthPixels
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = (width * 0.8f).toInt()
        dialog.window!!.attributes = lp
    }
}
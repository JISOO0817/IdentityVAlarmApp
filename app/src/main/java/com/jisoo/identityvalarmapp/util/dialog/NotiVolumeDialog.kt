package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.jisoo.identityvalarmapp.alarm.App
import com.jisoo.identityvalarmapp.databinding.DialogNotiVolumeBinding
import com.jisoo.identityvalarmapp.main.MainViewModel
import com.jisoo.identityvalarmapp.setting.SeekbarInterface
import com.warkiz.widget.IndicatorSeekBar

import com.warkiz.widget.SeekParams

import com.warkiz.widget.OnSeekChangeListener

class NotiVolumeDialog(context: Context, binding: DialogNotiVolumeBinding, model: MainViewModel):
    Dialog(context), SeekbarInterface {

    private var notiVolumeBinding: DialogNotiVolumeBinding
    private var model: MainViewModel

    init {
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        notiVolumeBinding = binding
        this.model = model
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        setContentView(binding.root)

        notiVolumeBinding.seekbar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams?) {
//                onSeeking(seekParams!!.thumbPosition)
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {
                Log.d("seekbar","onStartTrackingTouch: ${seekBar!!.progress}")
            }

            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
                Log.d("seekbar","onStopTrackingTouch: ${seekBar!!.progress}")
                onSeeking(seekBar.progress)
            }

        }
//        setSeekbarViewState()

    }

    fun setSeekbarViewState() {
        Log.d("seekbar","setSeekbarViewState() 호출")

        /**
         * 0 -> 무음
         * 50 -> 진동
         * 100 -> 소리
         * **/
        when(App.prefs.getAlarmImportance("alarm",-1)) {
            0 -> notiVolumeBinding.seekbar.setProgress(0f)
            50 -> notiVolumeBinding.seekbar.setProgress(50f)
            100 -> notiVolumeBinding.seekbar.setProgress(100f)
        }
    }

    fun setFontSize(titleFontSize: Float, closeFontSize: Float, confirmFontSize: Float) {
        notiVolumeBinding.volumeEditTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,titleFontSize)
        notiVolumeBinding.closeBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,closeFontSize)
        notiVolumeBinding.confirmBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP,confirmFontSize)
    }

    fun setMargin(titleMargin: Margin, seekMargin: Margin) {
        val titleLayoutParams = notiVolumeBinding.volumeEditTv.layoutParams as ConstraintLayout.LayoutParams
        titleLayoutParams.topMargin = titleMargin.topMargin
        titleLayoutParams.leftMargin = titleMargin.leftMargin
        titleLayoutParams.rightMargin = titleMargin.rightMargin
        notiVolumeBinding.volumeEditTv.layoutParams = titleLayoutParams
        notiVolumeBinding.volumeEditTv.requestLayout()

        val infoLayoutParams = notiVolumeBinding.seekbar.layoutParams as ConstraintLayout.LayoutParams
        infoLayoutParams.topMargin = seekMargin.topMargin
        infoLayoutParams.leftMargin = seekMargin.leftMargin
        infoLayoutParams.rightMargin = seekMargin.rightMargin
        notiVolumeBinding.seekbar.layoutParams = infoLayoutParams
        notiVolumeBinding.seekbar.requestLayout()
    }

    fun setBtnSize(btnHeight: Int) {
        val closeBtnLayoutParams = notiVolumeBinding.closeBtn.layoutParams as ConstraintLayout.LayoutParams
        closeBtnLayoutParams.height = btnHeight
        notiVolumeBinding.closeBtn.layoutParams = closeBtnLayoutParams
        notiVolumeBinding.closeBtn.requestLayout()

        val confirmBtnLayoutParams = notiVolumeBinding.confirmBtn.layoutParams as ConstraintLayout.LayoutParams
        confirmBtnLayoutParams.height = btnHeight
        notiVolumeBinding.confirmBtn.layoutParams = confirmBtnLayoutParams
        notiVolumeBinding.confirmBtn.requestLayout()
    }

    override fun onSeeking(status: Int) {
        model.onSeekbarListener(status)
    }

}
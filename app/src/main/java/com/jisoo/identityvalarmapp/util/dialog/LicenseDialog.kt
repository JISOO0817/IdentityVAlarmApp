package com.jisoo.identityvalarmapp.util.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.DialogLicenseBinding
import com.jisoo.identityvalarmapp.setting.license.LicenseAdapter
import com.jisoo.identityvalarmapp.setting.license.LicenseContent

class LicenseDialog(context: Context, binding: DialogLicenseBinding) : Dialog(context) {

    private var licenseBinding: DialogLicenseBinding
    private lateinit var adapter: LicenseAdapter

    init {
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        licenseBinding = binding
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if(binding.root.parent != null) {
            (binding.root.parent as ViewGroup).removeView(binding.root)
        }

        setContentView(binding.root)
        setUpRecyclerview()
    }


    private fun setUpRecyclerview() {
        val licenseRv = licenseBinding.licenseRv
        licenseRv.setHasFixedSize(true)

        this.adapter = LicenseAdapter(context)
        licenseRv.adapter = adapter

        addLicenseInfo()
    }

    private fun addLicenseInfo() {
        val list: ArrayList<LicenseContent> = arrayListOf()
        list.add(LicenseContent(context.getString(R.string.dialog_license_coroutine_txt),context.getString(R.string.dialog_license_coroutine_link)))
        list.add(LicenseContent(context.getString(R.string.dialog_license_room_txt),context.getString(R.string.dialog_license_room_link)))
        list.add(LicenseContent(context.getString(R.string.dialog_license_firebase_txt),context.getString(R.string.dialog_license_firebase_link)))
        list.add(LicenseContent(context.getString(R.string.dialog_license_circleimageview_txt),context.getString(R.string.dialog_license_circleimageview_link)))
        list.add(LicenseContent(context.getString(R.string.dialog_license_glide_txt),context.getString(R.string.dialog_license_glide_link)))
        list.add(LicenseContent(context.getString(R.string.dialog_license_chinesecalendar_txt),context.getString(R.string.dialog_license_chinesecalendar_link)))

        adapter.setData(list)
    }




}
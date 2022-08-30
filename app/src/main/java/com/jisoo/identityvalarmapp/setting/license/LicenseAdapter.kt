package com.jisoo.identityvalarmapp.setting.license

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jisoo.identityvalarmapp.databinding.ListItemLicenseBinding
import com.jisoo.identityvalarmapp.util.BindingAdapter

class LicenseAdapter(val context: Context) : RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder>() {
    private lateinit var list: List<LicenseContent>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LicenseViewHolder {
        return LicenseViewHolder(ListItemLicenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<LicenseContent>) {
        this.list = list
        notifyDataSetChanged()
    }

    class LicenseViewHolder(val binding: ListItemLicenseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(content: LicenseContent) {
            binding.titleTv.text = content.title
            binding.linkTv.text = content.link
            binding.linkTv.paintFlags = binding.linkTv.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            BindingAdapter.setOnLicenseClicked(binding.linkTv,content.link)
        }
    }
}
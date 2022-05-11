package com.jisoo.identityvalarmapp.main.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jisoo.identityvalarmapp.databinding.ItemListBinding
import com.jisoo.identityvalarmapp.main.OnToggleCallback
import com.jisoo.identityvalarmapp.model.CharacInfo

class ListAdapter : RecyclerView.Adapter<ListAdapter.HunViewHolder>(){

    private var list = ArrayList<CharacInfo>()
    private var onTogglecallback : OnToggleCallback ?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HunViewHolder {
        return HunViewHolder(ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: HunViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size

    }

    fun setData(it: List<CharacInfo>) {
        list = it as ArrayList<CharacInfo>
        notifyDataSetChanged()
    }

    fun addOnToggle(onToggleCallback: OnToggleCallback) {
        this.onTogglecallback = onToggleCallback
    }

    //TODO: inner class 빼기
    inner class HunViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(info: CharacInfo) {
            binding.jobTv.text = info.job
            binding.birthTv.text = info.birth
            binding.sw.isChecked = info.onoff
            binding.sw.setOnCheckedChangeListener {
                btn,isCheck ->
                info.onoff = isCheck
                onTogglecallback?.onToggle(info)
            }
        }
    }
}
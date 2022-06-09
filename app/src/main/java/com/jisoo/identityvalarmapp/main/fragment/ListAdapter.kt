package com.jisoo.identityvalarmapp.main.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jisoo.identityvalarmapp.R
import com.jisoo.identityvalarmapp.databinding.ItemListBinding
import com.jisoo.identityvalarmapp.main.OnClickCallback
import com.jisoo.identityvalarmapp.model.CharacInfo

class ListAdapter(val context: Context) : RecyclerView.Adapter<ListAdapter.HunViewHolder>(){

    private var list = ArrayList<CharacInfo>()
    private var onClickListener : OnClickCallback ?= null

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

    @SuppressLint("NotifyDataSetChanged")
    fun setData(it: List<CharacInfo>) {
        list = it as ArrayList<CharacInfo>
        notifyDataSetChanged()
    }

    fun onClickItem(onClickCallback: OnClickCallback) {
        this.onClickListener = onClickCallback
    }

    inner class HunViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("ResourceAsColor")
        fun bind(info: CharacInfo) {
//            info.executionAlarm(context)
//            if (info.onoff) {
//                binding.constraint.alpha = 1f
//                binding.img.alpha = 1f
//
//                binding.constraint.setBackgroundColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.white
//                    )
//                )
//            } else {
//                binding.constraint.alpha = 0.5f
//                binding.img.alpha = 0.5f
//                binding.constraint.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
//            }

//            binding.img.setBackgroundResource(info.img)

            Glide.with(context)
                .load(info.img)
                .into(binding.img)

            binding.testlayout.setOnClickListener {
                onClickListener?.onViewClick(list[adapterPosition])
            }
        }

    }
}
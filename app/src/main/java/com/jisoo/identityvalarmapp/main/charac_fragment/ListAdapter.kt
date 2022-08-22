package com.jisoo.identityvalarmapp.main.charac_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jisoo.identityvalarmapp.databinding.ItemListBinding
import com.jisoo.identityvalarmapp.model.CharacInfo
import kotlin.properties.Delegates

class ListAdapter(val context: Context) : RecyclerView.Adapter<ListAdapter.HunViewHolder>(){

    private var list = ArrayList<CharacInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HunViewHolder {
        return HunViewHolder(ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),this.context)
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

    class HunViewHolder(val binding: ItemListBinding,val context: Context) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(info: CharacInfo) {

            val bDay = info.birth.substring(2 until 4).toInt()
            val name = context.resources.getString(info.job)

            binding.birthDayTv.text = bDay.toString()
            binding.birthDayTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
            Glide.with(context)
                .load(info.img)
                .into(binding.img)

            binding.nameTv.text = name
            binding.nameTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        }

    }
}
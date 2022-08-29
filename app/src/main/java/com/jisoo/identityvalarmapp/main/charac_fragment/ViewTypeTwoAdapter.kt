package com.jisoo.identityvalarmapp.main.charac_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jisoo.identityvalarmapp.databinding.ItemList2Binding

import com.jisoo.identityvalarmapp.model.CharacInfo

class ViewTypeTwoAdapter(val context: Context) : RecyclerView.Adapter<ViewTypeTwoAdapter.TwoViewHolder>() {

    private var list: ArrayList<CharacInfo> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwoViewHolder {
        return TwoViewHolder(
            ItemList2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), this.context
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: TwoViewHolder, position: Int) {
        holder.bind(list[position])
//        holder.binding.root.requestLayout()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(it: List<CharacInfo>) {
        list.addAll(it)
        notifyDataSetChanged()
    }


    class TwoViewHolder(val binding: ItemList2Binding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(info: CharacInfo) {

            val bMonth = info.birth.substring(0 until 2).toInt()
            val bDay = info.birth.substring(2 until 4).toInt()
            val name = context.resources.getString(info.job)

            binding.birthDayTv.text = "$bMonth / $bDay"
            binding.birthDayTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13f)
            Glide.with(context)
                .load(info.img)
                .into(binding.img)

            binding.nameTv.text = name
            binding.nameTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f)
        }
    }
}
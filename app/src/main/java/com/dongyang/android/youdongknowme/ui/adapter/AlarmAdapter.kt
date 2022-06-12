package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import com.dongyang.android.youdongknowme.databinding.ItemAlarmBinding

class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    private val item = ArrayList<AlarmEntity>()

    inner class ViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlarmEntity) {
            binding.itemAlarmTitle.text = item.title
            binding.itemAlarmKeyword.text = item.keyword
            binding.itemAlarmDeparmtent.text = item.department
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<AlarmEntity>) {
        this.item.clear()
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAlarmBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int = item.size

}
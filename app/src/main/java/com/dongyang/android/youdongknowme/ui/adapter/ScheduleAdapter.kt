package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import com.dongyang.android.youdongknowme.data.remote.entity.ScheduleEntry
import com.dongyang.android.youdongknowme.databinding.ItemScheduleBinding

class ScheduleAdapter : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var item = arrayListOf<ScheduleEntry>()

    inner class ViewHolder(private val binding: ItemScheduleBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScheduleEntry) {
            binding.tvItemScheduleDate.text = if(item.dates[0] == item.dates[1]) item.dates[0] else "${item.dates[0]}\n~ ${item.dates[1]}"
            binding.tvItemScheduleContents.text = item.contents
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<ScheduleEntry>) {
        this.item.clear()
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemScheduleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int = item.size
}
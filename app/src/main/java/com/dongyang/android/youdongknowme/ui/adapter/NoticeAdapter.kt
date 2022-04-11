package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.databinding.ItemNoticeBinding
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeClickListener

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    private var item = arrayListOf<Notice>()
    private var itemClickListener : NoticeClickListener? = null

    inner class ViewHolder(private val binding: ItemNoticeBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item : Notice) {
                binding.notice = item
                binding.itemClickListener = itemClickListener
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item : List<Notice>) {
        this.item.clear()
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoticeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int = item.size

    fun setItemClickListener(listener : NoticeClickListener) {
        itemClickListener = listener
    }
}
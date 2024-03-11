package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.databinding.ItemNoticeBinding
import com.dongyang.android.youdongknowme.ui.viewholder.NoticeViewHolder

class NoticeAdapter(private val onItemClick: (url: String) -> Unit) :
    RecyclerView.Adapter<NoticeViewHolder>() {

    private val noticeList = arrayListOf<Notice>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<Notice>) {
        noticeList.clear()
        noticeList.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(
            ItemNoticeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClick
        )
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int = noticeList.size
}
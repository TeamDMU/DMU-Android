package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.databinding.ItemNoticeBinding
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeClickListener

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var noticeList = arrayListOf<Notice>()
    private var itemClickListener: NoticeClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<Notice>) {
        noticeList.clear()
        noticeList.addAll(item)
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
        holder.bind(noticeList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = noticeList.size

    fun setItemClickListener(listener: NoticeClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(private val binding: ItemNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notice) {
            binding.itemNoticeTitle.text = item.title
            binding.itemNoticeDate.text = item.date
            binding.itemNoticeAuthor.text = item.author
        }
    }
}
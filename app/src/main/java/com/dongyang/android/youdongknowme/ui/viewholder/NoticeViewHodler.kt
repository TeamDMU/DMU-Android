package com.dongyang.android.youdongknowme.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.databinding.ItemNoticeBinding

class NoticeViewHolder(
    private val binding: ItemNoticeBinding,
    clickListener: (url: String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private var noticeUrl: String? = null

    init {
        binding.root.setOnClickListener {
            clickListener(noticeUrl ?: "0")
        }
    }

    fun bind(item: Notice) {
        noticeUrl = item.url

        binding.tvNoticeTitle.text = item.title
        binding.tvNoticeDate.text = item.date
        binding.tvNoticeAuthor.text = item.author
    }
}
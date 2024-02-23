package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeFileUrl
import com.dongyang.android.youdongknowme.databinding.ItemFileBinding

class FileAdapter : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    private var item = arrayListOf<NoticeFileUrl>()
     private var itemClickListener : DetailClickListener? = null

    inner class ViewHolder(private val binding: ItemFileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoticeFileUrl) {
            binding.itemClickListener = itemClickListener
            binding.file = item
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<NoticeFileUrl>) {
        this.item.clear()
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFileBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int = item.size

    fun setItemClickListener(listener : DetailClickListener) {
        itemClickListener = listener
    }
}
package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeFileUrl
import com.dongyang.android.youdongknowme.databinding.ItemFileBinding

class FileAdapter : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    private var item = arrayListOf<NoticeFileUrl>()
    // private var itemClickListener : NoticeClickListener? = null

    inner class ViewHolder(private val binding: ItemFileBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoticeFileUrl) {
            if(item.name.isBlank()) {
                binding.itemFileBtn.visibility = View.GONE
                binding.itemFileNotFound.apply {
                    visibility = View.VISIBLE
                }
            } else {
                binding.itemFileBtn.text = item.name
                binding.itemFileNotFound.apply {
                    visibility = View.GONE
                }
            }
            // binding.itemClickListener = itemClickListener
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item : List<NoticeFileUrl>) {
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

//    fun setItemClickListener(listener : NoticeClickListener) {
//        itemClickListener = listener
//    }
}
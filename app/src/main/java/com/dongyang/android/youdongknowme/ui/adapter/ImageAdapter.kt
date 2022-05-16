package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeFileUrl
import com.dongyang.android.youdongknowme.databinding.ItemFileBinding
import com.dongyang.android.youdongknowme.databinding.ItemImageBinding
import com.dongyang.android.youdongknowme.standard.util.log

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var item = arrayListOf<String>()
    // private var itemClickListener : NoticeClickListener? = null

    inner class ViewHolder(private val binding: ItemImageBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            log(item)

            if(item.isNotEmpty()) {
                Glide.with(binding.root.context)
                    .load(item)
                    .into(binding.itemDetailIv)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item : List<String>) {
        this.item.clear()
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(
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
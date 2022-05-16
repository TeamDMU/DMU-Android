package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dongyang.android.youdongknowme.databinding.ItemImageBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var item = arrayListOf<String>()

    inner class ViewHolder(private val binding: ItemImageBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {

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

}
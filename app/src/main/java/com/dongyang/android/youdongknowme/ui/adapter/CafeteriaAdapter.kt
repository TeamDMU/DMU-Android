package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.databinding.ItemCafeteriaMenuBinding

class CafeteriaAdapter : RecyclerView.Adapter<CafeteriaAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var menu = arrayListOf<String>()

    inner class ViewHolder(private val binding: ItemCafeteriaMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.menu = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCafeteriaMenuBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menu[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<String>) {
        menu.clear()
        menu.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = menu.size
}
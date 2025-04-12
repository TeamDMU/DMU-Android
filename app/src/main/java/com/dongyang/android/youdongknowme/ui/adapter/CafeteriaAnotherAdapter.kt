package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.databinding.ItemCafeteriaAnotherBinding
import com.dongyang.android.youdongknowme.ui.viewholder.CafeteriaAnotherViewHolder

class CafeteriaAnotherAdapter : RecyclerView.Adapter<CafeteriaAnotherViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var menu = arrayListOf<Array<String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaAnotherViewHolder {
        return CafeteriaAnotherViewHolder(
            ItemCafeteriaAnotherBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: CafeteriaAnotherViewHolder, position: Int) {
        holder.bind(menu[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<Array<String>>) {
        menu.clear()
        menu.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = menu.size
}
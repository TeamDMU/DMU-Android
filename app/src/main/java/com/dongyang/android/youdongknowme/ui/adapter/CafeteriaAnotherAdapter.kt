package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ItemCafeteriaAnotherBinding
import com.google.android.material.internal.ViewUtils.dpToPx

class CafeteriaAnotherAdapter : RecyclerView.Adapter<CafeteriaAnotherAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private var menu = arrayListOf<Array<String>>()

    inner class ViewHolder(private val binding: ItemCafeteriaAnotherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Array<String>) {
            binding.menuKr = item[0]
            binding.menuEn = item[1].split('_')
                .joinToString(" ") { word ->
                    word.lowercase().replaceFirstChar { firstChar -> firstChar.uppercase() }
                }
            binding.menuPrice = item[2]

            val context = binding.root.context
            val resourceName = "img_cafeteria_" + item[1].lowercase()
            val drawableResId = context.resources.getIdentifier(resourceName, "drawable", context.packageName)

            if (drawableResId != 0) {
                binding.imgAnother.setImageResource(drawableResId)
                binding.imgAnother.setPadding(0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCafeteriaAnotherBinding.inflate(
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
    fun submitList(item: List<Array<String>>) {
        menu.clear()
        menu.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = menu.size
}
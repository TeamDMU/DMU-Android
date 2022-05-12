package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ItemDepartBinding

class DepartAdapter : RecyclerView.Adapter<DepartAdapter.ViewHolder>() {

    private val item = arrayListOf<String>()
    private var selectedPosition = -1
    private var beforePosition = -1

    inner class ViewHolder(private val binding: ItemDepartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.itemDepartName.text = item

            binding.itemDepartContainer.setOnClickListener {
                beforePosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(selectedPosition)
                notifyItemChanged(beforePosition)
            }

            if(selectedPosition == position) {
                binding.itemDepartCheck.visibility = View.VISIBLE
                binding.itemDepartName.typeface = Typeface.DEFAULT_BOLD
                binding.itemDepartName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.sub))
            } else {
                binding.itemDepartCheck.visibility = View.INVISIBLE
                binding.itemDepartName.typeface = Typeface.DEFAULT
                binding.itemDepartName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: ArrayList<String>) {
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDepartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position], position)
    }

    override fun getItemCount(): Int = item.size

//    fun setItemClickListener(listener : NoticeClickListener) {
//        itemClickListener = listener
//    }
}
package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ItemDepartBinding
import com.dongyang.android.youdongknowme.ui.view.depart.DepartClickListener

class DepartAdapter : RecyclerView.Adapter<DepartAdapter.ViewHolder>() {

    private val item = ArrayList<String>()
    private var itemClickListener: DepartClickListener? = null
    private var currentPosition = -1
    private var beforePosition = -1

    inner class ViewHolder(private val binding: ItemDepartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForColorStateLists")
        fun bind(item: String, position: Int) {
            binding.itemDepartName.text = item
            binding.itemClickListener = itemClickListener
            binding.currentPosition = position

            // 선택한 포지션이 맞을 때 강조 표시
            if (currentPosition == position) {
                binding.itemDepartContainer.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.blue300
                    )
                )
                binding.itemDepartName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
            } else {
                binding.itemDepartContainer.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
                binding.itemDepartName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray400
                    )
                )

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: ArrayList<String>) {
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    fun submitPosition(currentPosition: Int) {
        this.beforePosition = this.currentPosition
        this.currentPosition = currentPosition
        notifyItemChanged(beforePosition)
        notifyItemChanged(this.currentPosition)
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

    fun setItemClickListener(listener: DepartClickListener) {
        itemClickListener = listener
    }
}
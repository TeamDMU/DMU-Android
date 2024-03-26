package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.databinding.ItemOnboardingDepartBinding
import com.dongyang.android.youdongknowme.ui.view.depart.DepartClickListener


class OnboardingDepartAdapter : RecyclerView.Adapter<OnboardingDepartAdapter.ViewHolder>() {

    private var item = ArrayList<String>()
    private var itemClickListener: DepartClickListener? = null
    private var currentPosition = -1
    private var beforePosition = -1

    inner class ViewHolder(private val binding: ItemOnboardingDepartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForColorStateLists")
        fun bind(item: String, position: Int) {
            binding.itemOnboardingDepartName.text = item
            binding.itemClickListener = itemClickListener
            binding.currentPosition = position
        }
    }

    fun setItems(list: ArrayList<String>) {
        this.item = list
        notifyDataSetChanged()
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
            ItemOnboardingDepartBinding.inflate(
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
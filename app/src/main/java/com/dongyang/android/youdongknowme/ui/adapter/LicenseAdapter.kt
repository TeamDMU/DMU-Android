package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.data.local.entity.OpenSourceEntity
import com.dongyang.android.youdongknowme.databinding.ItemLicenseBinding
import com.dongyang.android.youdongknowme.ui.view.license.LicenseClickListener

class LicenseAdapter : RecyclerView.Adapter<LicenseAdapter.ViewHolder>() {

    private val item = ArrayList<OpenSourceEntity>()
    private var itemClickListener: LicenseClickListener? = null

    inner class ViewHolder(private val binding: ItemLicenseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OpenSourceEntity) {
            binding.openSource = item
            binding.itemClickListener = itemClickListener
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(item: List<OpenSourceEntity>) {
        this.item.clear()
        this.item.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLicenseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(item[position])

    override fun getItemCount(): Int = item.size

    fun setItemClickListener(listener: LicenseClickListener) {
        itemClickListener = listener
    }
}
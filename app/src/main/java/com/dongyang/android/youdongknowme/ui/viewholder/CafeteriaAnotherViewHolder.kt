package com.dongyang.android.youdongknowme.ui.viewholder

import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ItemCafeteriaAnotherBinding

class CafeteriaAnotherViewHolder(
    private val binding: ItemCafeteriaAnotherBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Array<String>) {
        binding.menuKr = item[0]
        binding.menuEn = item[1].split('_')
            .joinToString(" ") { word ->
                word.lowercase().replaceFirstChar { firstChar -> firstChar.uppercase() }
            }
        binding.menuPrice = item[2]

        val context = binding.root.context
        val resourceName = "img_cafeteria_" + item[1].lowercase()
        val drawableResId =
            context.resources.getIdentifier(resourceName, "drawable", context.packageName)

        if (drawableResId != 0) {
            binding.imgAnother.setImageResource(drawableResId)
            binding.imgAnother.setPadding(0)
        } else {
            binding.imgAnother.setImageResource(R.drawable.img_cafeteria_korean)
            binding.imgAnother.setPadding(15)
        }
    }
}
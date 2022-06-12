package com.dongyang.android.youdongknowme.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.dongyang.android.youdongknowme.R

@BindingAdapter("bind_visibility_search")
fun bindSearchView(view: View, condition: Boolean) {
    view.visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_visibility_existence")
fun bindFileList(view: View, condition: Boolean) {
    view.visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_visibility_notice_no_data")
fun bindNoticeNoData(view: View, condition: Boolean) {
    view.visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_visibility_on_loading")
fun bindLoadingVisible(view: View, condition: Boolean) {
    view.visibility = if (condition) View.GONE else View.VISIBLE
}

@BindingAdapter("bind_visibility_is_first_launch")
fun bindIsFirstLaunch(view: View, condition: Boolean) {
    view.visibility = if (condition) View.GONE else View.VISIBLE
}

@BindingAdapter("bind_is_checked")
fun bindIsChecked(view: SwitchCompat, condition: Boolean) {
    view.isChecked = condition
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("bind_background")
fun bindIsVisited(view: View, condition: Boolean) {
    view.background =
        if (condition) view.context.getDrawable(R.color.white) else view.context.getDrawable(R.color.lightMain)
}
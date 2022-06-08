package com.dongyang.android.youdongknowme.ui.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bind_visibility_search")
fun bindSearchView(view : View, condition: Boolean) {
    view.visibility = if(condition) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_visibility_existence")
fun bindFileList(view : View, condition: Boolean) {
    view.visibility = if(condition) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_visibility_notice_no_data")
fun bindNoticeNoData(view : View, condition: Boolean) {
    view.visibility = if(condition) View.VISIBLE else View.GONE
}

@BindingAdapter("bind_visibility_on_loading")
fun bindLoadingVisible(view : View, condition: Boolean) {
    view.visibility = if(condition) View.GONE else View.VISIBLE
}
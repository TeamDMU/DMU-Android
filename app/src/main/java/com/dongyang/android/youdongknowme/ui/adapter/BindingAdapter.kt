package com.dongyang.android.youdongknowme.ui.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bind_is_loading", "bind_is_error")
fun bindShowScreen(view: View, isLoading: Boolean, isError: Boolean) {
    view.visibility = if (isLoading || isError) View.GONE else View.VISIBLE
}
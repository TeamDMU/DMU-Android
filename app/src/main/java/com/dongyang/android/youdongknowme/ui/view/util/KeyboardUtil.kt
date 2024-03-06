package com.dongyang.android.youdongknowme.ui.view.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
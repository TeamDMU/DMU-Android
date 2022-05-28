package com.dongyang.android.youdongknowme.standard.util

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

fun logd(message : String) = Log.d("YouDongKnowMe", message)
fun logw(message : String) = Log.w("YouDongKnowMe", message)
fun loge(message : String) = Log.e("YouDongKnowMe", message)

fun View.showKeyboard(isForced: Boolean = false) {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, if (isForced) InputMethodManager.SHOW_FORCED else InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
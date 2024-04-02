package com.dongyang.android.youdongknowme.standard.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.showKeyboard(isForced: Boolean = false) {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(
        this,
        if (isForced) InputMethodManager.SHOW_FORCED else InputMethodManager.SHOW_IMPLICIT
    )
}

fun View.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Int.dpToPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}


object ACTION {
    const val FCM_ACTION_NAME = "com.google.firebase.MESSAGING_EVENT"
}
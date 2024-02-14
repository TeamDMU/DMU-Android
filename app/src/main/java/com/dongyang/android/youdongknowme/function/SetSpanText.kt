package com.dongyang.android.youdongknowme.function

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.dongyang.android.youdongknowme.R

fun setSpanText(mContext: Context, spanTextView: TextView, startIdx: Int, endIdx: Int) {
    SpannableStringBuilder(spanTextView.text).apply {
        setSpan(
            ForegroundColorSpan(getColor(mContext, R.color.blue300)),
            startIdx,
            endIdx,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanTextView.text = this
    }
}

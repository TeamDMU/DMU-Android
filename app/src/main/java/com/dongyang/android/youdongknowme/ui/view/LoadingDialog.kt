package com.dongyang.android.youdongknowme.ui.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.dongyang.android.youdongknowme.R

class LoadingDialog(context: Context) : Dialog(context) {

    init {
        setCanceledOnTouchOutside(false)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_loading)
    }
}
package com.dongyang.android.youdongknowme.ui.view.license

import com.dongyang.android.youdongknowme.data.local.entity.OpenSourceEntity

interface LicenseClickListener {
    fun itemClick(openSourceEntity: OpenSourceEntity)
}
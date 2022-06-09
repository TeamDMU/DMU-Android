package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference

class SplashRepository {
    fun getIsFirstLaunch(): Boolean? = SharedPreference.getIsFirstLaunch()
}
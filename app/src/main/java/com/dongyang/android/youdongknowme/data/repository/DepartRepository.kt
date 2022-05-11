package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference

class DepartRepository {
    fun setDepartment(department : String) {
        SharedPreference.setDepartment(department)
    }
}
package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.standard.util.Department

class DepartRepository {
    fun setDepartment(departName: String) {
        val department = Department.getDepartment(departName)
        SharedPreference.setDepartment(department.name)
        SharedPreference.setCode(department.code)
    }

    fun getUserDepartment(): String {
        return SharedPreference.getDepartment()
    }
}
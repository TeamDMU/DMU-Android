package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference

class SettingRepository {

    fun getIsAccessSchoolAlarm(): Boolean = SharedPreference.getIsAccessSchoolAlarm()

    fun getIsAccessDepartAlarm(): Boolean = SharedPreference.getIsAccessDepartAlarm()

    fun setIsAccessSchoolAlarm(isAccessSchoolAlarm: Boolean) {
        SharedPreference.setIsAccessSchoolAlarm(isAccessSchoolAlarm)
    }

    fun setIsAccessDepartAlarm(isAccessDepartAlarm: Boolean) {
        SharedPreference.setIsAccessDepartAlarm(isAccessDepartAlarm)
    }

    fun getUserDepartment(): String {
        return SharedPreference.getDepartment()
    }
}
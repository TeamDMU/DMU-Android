package com.dongyang.android.youdongknowme.data.local

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.dongyang.android.youdongknowme.R

object SharedPreference {
    private var pref: SharedPreferences? = null

    fun getInstance(context: Context): SharedPreference {
        if (pref == null) {
            pref = context.getSharedPreferences(
                context.getString(R.string.shared_pref_key),
                Application.MODE_PRIVATE
            )
        }
        return this
    }

    // 학과 설정
    private const val DEPARTMENT = "DEPARTMENT"
    fun getDepartment(): String? = pref?.getString(DEPARTMENT, "")
    fun setDepartment(department: String) = pref?.edit()?.putString(DEPARTMENT, department)?.apply()

    // 학과 코드 설정
    private const val CODE = "CODE"
    fun getCode(): Int? = pref?.getInt(CODE, 0)
    fun setCode(code: Int) = pref?.edit()?.putInt(CODE, code)?.apply()

    // 학사 일정
    private const val SCHEDULE = "SCHEDULE"
    fun getSchedule(): String? = pref?.getString(SCHEDULE, "No Data")
    fun setSchedule(schedule: String) = pref?.edit()?.putString(SCHEDULE, schedule)?.apply()

    // 최초 접속 여부
    private const val FIRST_LAUNCH = "FIRST_LAUNCH"
    fun getIsFirstLaunch(): Boolean? = pref?.getBoolean(FIRST_LAUNCH, true)
    fun setIsFirstLaunch(isFirstLaunch: Boolean) = pref?.edit()?.putBoolean(FIRST_LAUNCH, isFirstLaunch)?.apply()
}
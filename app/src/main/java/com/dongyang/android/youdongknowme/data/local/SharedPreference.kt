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
    fun getDepartment(): String = pref?.getString(DEPARTMENT, "") ?: ""
    fun setDepartment(department: String) = pref?.edit()?.putString(DEPARTMENT, department)?.apply()

    // 학과 코드 설정
    private const val CODE = "CODE"
    fun getCode(): Int = pref?.getInt(CODE, 0) ?: 0
    fun setCode(code: Int) = pref?.edit()?.putInt(CODE, code)?.apply()

    // 학사 일정
    private const val SCHEDULE = "SCHEDULE"
    const val NO_SCHEDULE = "NO DATA"
    fun getSchedule(): String = pref?.getString(SCHEDULE, NO_SCHEDULE) ?: NO_SCHEDULE
    fun setSchedule(schedule: String) = pref?.edit()?.putString(SCHEDULE, schedule)?.apply()

    // 최초 접속 여부
    private const val FIRST_LAUNCH = "FIRST_LAUNCH"
    fun getIsFirstLaunch(): Boolean = pref?.getBoolean(FIRST_LAUNCH, true) ?: true
    fun setIsFirstLaunch(isFirstLaunch: Boolean) = pref?.edit()?.putBoolean(FIRST_LAUNCH, isFirstLaunch)?.apply()

    // 학교 알림 설정 여부
    private const val ACCESS_SCHOOL_ALARM = "ACCESS_SCHOOL_ALARM"
    fun getIsAccessSchoolAlarm(): Boolean = pref?.getBoolean(ACCESS_SCHOOL_ALARM, true) ?: true
    fun setIsAccessSchoolAlarm(isAccessSchoolAlarm: Boolean) = pref?.edit()?.putBoolean(ACCESS_SCHOOL_ALARM, isAccessSchoolAlarm)?.apply()

    // 학과 알림 설정 여부
    private const val ACCESS_DEPART_ALARM = "ACCESS_DEPART_ALARM"
    fun getIsAccessDepartAlarm(): Boolean = pref?.getBoolean(ACCESS_DEPART_ALARM, true) ?: true
    fun setIsAccessDepartAlarm(isAccessDepartAlarm: Boolean) = pref?.edit()?.putBoolean(ACCESS_DEPART_ALARM, isAccessDepartAlarm)?.apply()

}
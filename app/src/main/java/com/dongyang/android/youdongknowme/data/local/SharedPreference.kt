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

}
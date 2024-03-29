package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.remote.entity.RemoveDepartment
import com.dongyang.android.youdongknowme.data.remote.entity.UpdateDepartment
import com.dongyang.android.youdongknowme.data.remote.service.SettingService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject

class SettingRepository(
    private val errorResponseHandler: ErrorResponseHandler
) {

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

    fun getUserFCMToken(): String {
        return SharedPreference.getFCMToken()
    }

    suspend fun updateUserDepartment(
        data: UpdateDepartment
    ): NetworkResult<Unit> {
        return try {
            val response = RetrofitObject.getNetwork().create(SettingService::class.java)
                .updateDepartment(data)
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    suspend fun removeUserDepartment(
        token: RemoveDepartment
    ): NetworkResult<Unit> {
        return try {
            val response = RetrofitObject.getNetwork().create(SettingService::class.java)
                .deleteDepartment(token)
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }
}
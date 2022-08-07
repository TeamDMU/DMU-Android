package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import com.dongyang.android.youdongknowme.data.remote.service.ScheduleService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject

class ScheduleRepository(
    private val errorResponseHandler: ErrorResponseHandler
) {
    suspend fun fetchSchedules(): NetworkResult<List<Schedule>> {
        return try {
            val response = RetrofitObject.getNetwork().create(ScheduleService::class.java).getScheduleList()
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    fun setLocalSchedules(schedule: String) {
        SharedPreference.setSchedule(schedule)
    }

    fun getLocalSchedules(): String? {
        return SharedPreference.getSchedule()
    }
}
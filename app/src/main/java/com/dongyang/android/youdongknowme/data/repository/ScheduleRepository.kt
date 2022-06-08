package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import com.dongyang.android.youdongknowme.data.remote.service.ScheduleService
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import retrofit2.Response

class ScheduleRepository {
    suspend fun fetchSchedules(): Response<List<Schedule>> {
        return RetrofitObject.getNetwork().create(ScheduleService::class.java).getScheduleList()
    }

    fun setLocalSchedules(schedule: String) {
        SharedPreference.setSchedule(schedule)
    }

    fun getLocalSchedules(): String {
        return SharedPreference.getSchedule()!!
    }
}
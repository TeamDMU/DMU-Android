package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import retrofit2.http.GET

interface ScheduleService {
    @GET("api/v1/dmu/scheduler")
    suspend fun getScheduleList(): ArrayList<Schedule>
}
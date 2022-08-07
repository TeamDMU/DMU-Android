package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleService {
    @GET("/schedule")
    suspend fun getScheduleList(
    ) : List<Schedule>
}
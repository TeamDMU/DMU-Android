package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.remote.entity.Schedule
import com.dongyang.android.youdongknowme.data.remote.service.ScheduleService
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import retrofit2.Response

class ScheduleRepository {
    suspend fun getNoticeList() : Response<List<Schedule>> {
        return RetrofitObject.getNetwork().create(ScheduleService::class.java).getScheduleList()
    }
}
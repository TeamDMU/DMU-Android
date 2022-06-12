package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.local.dao.AlarmDao
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.service.NoticeService
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import retrofit2.Response

class NoticeRepository(private val alarmDao: AlarmDao) {
    suspend fun fetchNotices(code : Int) : Response<List<Notice>> {
        return RetrofitObject.getNetwork().create(NoticeService::class.java).getList(code)
    }

    suspend fun fetchSearchNotices(code : Int, keyword : String) : Response<List<Notice>> {
        return RetrofitObject.getNetwork().create(NoticeService::class.java).getSearchList(code, keyword)
    }

    fun getUnVisitedAlarmCount() : Flow<Int> = alarmDao.getUnVisitedAlarmCount()

    fun getDepartmentCode(): Int? = SharedPreference.getCode()
}
package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.local.dao.AlarmDao
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.service.NoticeService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NoticeRepository(
    private val alarmDao: AlarmDao,
    private val errorResponseHandler: ErrorResponseHandler
) {
    suspend fun fetchNotices(code: Int): NetworkResult<List<Notice>> {
        return try {
            val response = RetrofitObject.getNetwork().create(NoticeService::class.java).getList(code)
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    suspend fun fetchSearchNotices(code: Int, keyword: String): NetworkResult<List<Notice>> {
        return try {
            val response = RetrofitObject.getNetwork().create(NoticeService::class.java).getSearchList(code, keyword)
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    fun getUnVisitedAlarmCount(): Flow<Int> = alarmDao.getUnVisitedAlarmCount()

    fun getDepartmentCode(): Int? = SharedPreference.getCode()
}
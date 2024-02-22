package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.local.dao.AlarmDao
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.service.NoticeService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import kotlinx.coroutines.flow.Flow

class NoticeRepository(
    private val alarmDao: AlarmDao,
    private val errorResponseHandler: ErrorResponseHandler
) {
    suspend fun fetchAllNotices(): NetworkResult<Map<String, List<Notice>>> {
        return try {
            val universityNotice = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getUniversityNotice(1, 20)
            val departNotices = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getDepartmentNotice("기계공학과", 1, 20)

            val result: Map<String, List<Notice>> =
                mapOf("school" to universityNotice, "depart" to departNotices)

            NetworkResult.Success(result)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    suspend fun fetchNotices(code: Int): NetworkResult<List<Notice>> {
        return try {
            val response =
                RetrofitObject.getNetwork().create(NoticeService::class.java)
                    .getUniversityNotice(1, 10)
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    fun getDepartmentCode(): Int = SharedPreference.getCode()
}
package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.service.NoticeService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject

class NoticeRepository(
    private val errorResponseHandler: ErrorResponseHandler
) {
    suspend fun fetchUniversityNotices(): NetworkResult<List<Notice>> {
        return try {
            val universityNotice = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getUniversityNotice(1, 20)

            NetworkResult.Success(universityNotice)
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
package com.dongyang.android.youdongknowme.data.repository

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
            val universityNotices = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getUniversityNotice(1, 20)

            NetworkResult.Success(universityNotices)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    suspend fun fetchDepartmentNotices(department: String): NetworkResult<List<Notice>> {
        return try {
            val departmentNotices = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getDepartmentNotice(department, 1, 20)
            NetworkResult.Success(departmentNotices)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }
}
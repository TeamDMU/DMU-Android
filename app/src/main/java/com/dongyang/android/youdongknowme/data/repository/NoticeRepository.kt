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

    fun getUserDepartment(): String {
        return SharedPreference.getDepartment()
    }

    suspend fun fetchUniversityNotices(page: Int): NetworkResult<List<Notice>> {
        return try {
            val universityNotices = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getUniversityNotice(page, DEFAULT_SIZE)

            NetworkResult.Success(universityNotices)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    suspend fun fetchDepartmentNotices(
        department: String,
        page: Int
    ): NetworkResult<List<Notice>> {
        return try {
            val departmentNotices = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getDepartmentNotice(department, page, DEFAULT_SIZE)
            NetworkResult.Success(departmentNotices)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    suspend fun fetchSearchNotices(
        searchWord: String,
        department: String,
        page: Int
    ): NetworkResult<List<Notice>> {
        return try {
            val searchNotices = RetrofitObject.getNetwork().create(NoticeService::class.java)
                .getSearchNotice(searchWord, department, page, DEFAULT_SIZE)
            NetworkResult.Success(searchNotices)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    companion object {
        private const val DEFAULT_SIZE = 20
    }
}
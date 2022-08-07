package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import com.dongyang.android.youdongknowme.data.remote.service.DetailService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject

class DetailRepository(
    private val errorResponseHandler: ErrorResponseHandler
) {
    suspend fun fetchNoticeDetail(code: Int, num: Int): NetworkResult<NoticeDetail> {

        return try {
            val response = RetrofitObject.getNetwork().create(DetailService::class.java)
                .getNoticeDetail(code, num)
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }
}
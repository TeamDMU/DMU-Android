package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("/notice/{code}/{number}")
    suspend fun getNoticeDetail(
        @Path("code") code : Int,
        @Path("number") number : Int
    ) : Response<NoticeDetail>
}
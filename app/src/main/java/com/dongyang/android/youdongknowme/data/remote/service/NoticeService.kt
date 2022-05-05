package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NoticeService {
    @GET("/notice")
    suspend fun getList(

    ) : Response<List<Notice>>

    @GET("/notice/{number}")
    suspend fun getNoticeDetail(
        @Path ("number") number : Int
    ) : Response<NoticeDetail>
}


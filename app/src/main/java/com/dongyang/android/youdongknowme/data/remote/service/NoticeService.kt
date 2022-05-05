package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoticeService {
    @GET("/notice")
    suspend fun getList(

    ) : Response<List<Notice>>

    @GET("/notice/search")
    suspend fun getSearchList(
        @Query("keyword") keyword : String
    ) : Response<List<Notice>>
}


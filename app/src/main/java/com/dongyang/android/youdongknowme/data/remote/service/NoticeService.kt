package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NoticeService {
    @GET("/notice/{code}")
    suspend fun getList(
        @Path("code") code : Int
    ) : List<Notice>

    @GET("/notice/{code}/search")
    suspend fun getSearchList(
        @Path("code") code : Int,
        @Query("keyword") keyword : String
    ) : List<Notice>
}


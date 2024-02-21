package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeService {
    @GET("api/v1/dmu/universityNotice")
    suspend fun getUniversityNotice(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<Notice>
}


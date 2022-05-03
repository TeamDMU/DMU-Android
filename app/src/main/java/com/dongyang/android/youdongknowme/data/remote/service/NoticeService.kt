package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import retrofit2.Response
import retrofit2.http.GET

interface NoticeService {
    @GET("/notice")
    suspend fun getList(

    ) : Response<List<Notice>>
}


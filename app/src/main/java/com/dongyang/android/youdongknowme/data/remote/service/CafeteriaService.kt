package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import retrofit2.http.GET

interface CafeteriaService {
    @GET("/api/v1/dmu/diet")
    suspend fun getMenuList() : List<Cafeteria>
}
package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import retrofit2.http.GET

interface CafeteriaService {
    @GET("/menu")
    suspend fun getMenuList() : List<Cafeteria>
}
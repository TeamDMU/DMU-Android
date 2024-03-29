package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.RemoveDepartment
import com.dongyang.android.youdongknowme.data.remote.entity.UpdateDepartment
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingService {

    @POST("department/v1/dmu/updateDepartment")
    suspend fun updateDepartment(@Body data: UpdateDepartment)

    @POST("department/v1/dmu/deleteDepartment")
    suspend fun deleteDepartment(@Body token: RemoveDepartment)
}
package com.dongyang.android.youdongknowme.data.remote.service

import com.dongyang.android.youdongknowme.data.remote.entity.RemoveToken
import com.dongyang.android.youdongknowme.data.remote.entity.UpdateDepartment
import com.dongyang.android.youdongknowme.data.remote.entity.UpdateTopic
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingService {

    @POST("department/v1/dmu/updateDepartment")
    suspend fun updateDepartment(@Body data: UpdateDepartment)

    @POST("department/v1/dmu/deleteDepartment")
    suspend fun deleteDepartment(@Body token: RemoveToken)

    @POST("token/v1/dmu/updateTopic")
    suspend fun updateTopic(@Body data: UpdateTopic)

    @POST("token/v1/dmu/deleteTopic")
    suspend fun deleteTopic(@Body token: RemoveToken)
}
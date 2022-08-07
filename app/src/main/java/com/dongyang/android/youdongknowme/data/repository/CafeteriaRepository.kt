package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.remote.service.CafeteriaService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject

class CafeteriaRepository(
    private val errorResponseHandler: ErrorResponseHandler
) {

    suspend fun fetchMenuList(code: Int, num: Int): NetworkResult<List<Cafeteria>> {
        return try {
            val response = RetrofitObject.getNetwork().create(CafeteriaService::class.java).getMenuList()
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }
}
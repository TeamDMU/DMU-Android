package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.local.dao.KeywordDao
import com.dongyang.android.youdongknowme.data.remote.service.TokenService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject

class MainRepository(
    private val keywordDao: KeywordDao,
    private val errorResponseHandler: ErrorResponseHandler
) {
    fun getIsFirstLaunch(): Boolean = SharedPreference.getIsFirstLaunch()

    fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        SharedPreference.setIsFirstLaunch(isFirstLaunch)
    }

    fun getUserDepartment(): String {
        return SharedPreference.getDepartment()
    }

    suspend fun getUserTopic(): List<String> {
        val subscribedTopic = keywordDao.getSubscribedKeywords()
        return subscribedTopic.map { it.englishName }
    }

    fun getFCMToken(): String {
        return SharedPreference.getFCMToken()
    }

    fun setFCMToken(token: String) {
        SharedPreference.setFcmToken(token)
    }

    suspend fun setUserToken(
        data: com.dongyang.android.youdongknowme.data.remote.entity.Token
    ): NetworkResult<Unit> {
        return try {
            val response = RetrofitObject.getNetwork().create(TokenService::class.java)
                .setInitToken(data)
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }
}
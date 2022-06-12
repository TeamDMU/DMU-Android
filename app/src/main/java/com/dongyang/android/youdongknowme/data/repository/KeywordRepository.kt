package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.local.dao.KeywordDao
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import kotlinx.coroutines.flow.Flow

class KeywordRepository(
    private val keywordDao: KeywordDao
) {
    fun getUserKeywords(): Flow<List<KeywordEntity>> {
        return keywordDao.getAllKeyword()
    }

    suspend fun updateUserKeywords(isSubscribe: Boolean, name: String) {
        keywordDao.updateKeyword(isSubscribe, name)
    }

    fun getIsFirstLaunch(): Boolean? = SharedPreference.getIsFirstLaunch()

    fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        SharedPreference.setIsFirstLaunch(isFirstLaunch)
    }
}
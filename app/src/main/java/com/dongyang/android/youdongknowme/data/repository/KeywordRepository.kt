package com.dongyang.android.youdongknowme.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dongyang.android.youdongknowme.data.local.dao.UserDao
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity

class KeywordRepository(
    private val userDao: UserDao
) {

    fun getUserKeywords(): LiveData<List<KeywordEntity>> {
        return userDao.getAllKeyword().asLiveData()
    }

    suspend fun updateUserKeywords(isSubscribe: Boolean, name: String) {
        userDao.updateKeyword(isSubscribe, name)
    }
}
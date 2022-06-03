package com.dongyang.android.youdongknowme.ui.view.keyword

import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.repository.KeywordRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.util.logd
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class KeywordViewModel(
    private val keywordRepository: KeywordRepository
) : BaseViewModel() {

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    private val _localKeywordList = keywordRepository.getLocalKeywordList()
    val localKeywordList get() = _localKeywordList

    private val _checkKeywordList = mutableSetOf<String>()
    val checkKeywordList get() = _checkKeywordList

    fun subscribeCheckKeyword() {
        for (localKeyword in localKeywordList.value!!) {
            if (checkKeywordList.contains(localKeyword.name)) {
                // 선택했던 데이터를 중첩해서 바꾸면 효율성이 떨어지고, 파이어베이스 구독에 문제가 생길 수 있으므로 구독 여부도 함께 체크
                if (!localKeyword.isSubscribe) {
                    logd("contains ${localKeyword.name}")
                    viewModelScope.launch {
                        keywordRepository.updateLocalKeyword(true, localKeyword.name)
                    }
                    firebaseMessaging.subscribeToTopic(localKeyword.englishName)
                }
            } else {
                if (localKeyword.isSubscribe) {
                    logd("not contains ${localKeyword.name}")
                    viewModelScope.launch {
                        keywordRepository.updateLocalKeyword(false, localKeyword.name)
                    }
                    firebaseMessaging.unsubscribeFromTopic(localKeyword.englishName)
                }
            }
        }
    }

    fun addAllCheckKeywordList(keyword: List<String>) {
        _checkKeywordList.addAll(keyword)
    }

    fun addCheckKeywordList(keyword: String) {
        _checkKeywordList.add(keyword)
    }

    fun removeCheckKeywordList(keyword: String) {
        _checkKeywordList.remove(keyword)
    }
}
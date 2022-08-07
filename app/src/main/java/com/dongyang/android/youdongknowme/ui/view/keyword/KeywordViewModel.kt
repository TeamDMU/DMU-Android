package com.dongyang.android.youdongknowme.ui.view.keyword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import com.dongyang.android.youdongknowme.data.repository.KeywordRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.util.logd
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class KeywordViewModel(
    private val keywordRepository: KeywordRepository
) : BaseViewModel() {

    private val firebaseMessaging = FirebaseMessaging.getInstance()

    private val _isFirstLaunch: MutableLiveData<Boolean> = MutableLiveData(false)
    val isFirstLaunch: LiveData<Boolean> get() = _isFirstLaunch

    private val _localKeywordList: MutableLiveData<List<KeywordEntity>> = MutableLiveData(emptyList())
    val localKeywordList: LiveData<List<KeywordEntity>> get() = _localKeywordList

    private val _checkKeywordList = mutableSetOf<String>()
    val checkKeywordList get() = _checkKeywordList

    fun getLocalKeywordList() {
        viewModelScope.launch {
            keywordRepository.getUserKeywords().collect { keywordList ->
                _localKeywordList.postValue(keywordList)
            }
        }
    }

    fun checkFirstLaunch() {
        if (keywordRepository.getIsFirstLaunch()) {
            _isFirstLaunch.value = true
        }
    }

    fun setFirstLaunch(isFirstLaunch: Boolean) {
        keywordRepository.setIsFirstLaunch(isFirstLaunch)
    }

    fun subscribeCheckedKeyword() {
        for (localKeyword in localKeywordList.value ?: emptyList()) {
            if (checkKeywordList.contains(localKeyword.name)) {
                // 선택했던 데이터를 중첩해서 바꾸면 효율성이 떨어지고, 파이어베이스 구독에 문제가 생길 수 있으므로 구독 여부도 함께 체크
                if (!localKeyword.isSubscribe) {
                    logd("contains ${localKeyword.name}")
                    viewModelScope.launch {
                        keywordRepository.updateUserKeywords(true, localKeyword.name)
                    }
                    firebaseMessaging.subscribeToTopic(localKeyword.englishName)
                }
            } else {
                if (localKeyword.isSubscribe) {
                    logd("not contains ${localKeyword.name}")
                    viewModelScope.launch {
                        keywordRepository.updateUserKeywords(false, localKeyword.name)
                    }
                    firebaseMessaging.unsubscribeFromTopic(localKeyword.englishName)
                }
            }
        }
    }

    fun setAllKeywords(keyword: List<String>) {
        _checkKeywordList.addAll(keyword)
    }

    fun setCheckedKeywords(keyword: String) {
        _checkKeywordList.add(keyword)
    }

    fun removeCheckedKeywords(keyword: String) {
        _checkKeywordList.remove(keyword)
    }
}
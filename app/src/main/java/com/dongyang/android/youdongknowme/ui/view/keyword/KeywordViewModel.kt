package com.dongyang.android.youdongknowme.ui.view.keyword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import com.dongyang.android.youdongknowme.data.repository.KeywordRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import kotlinx.coroutines.launch

class KeywordViewModel(
    private val keywordRepository: KeywordRepository
) : BaseViewModel() {

    private val _localKeywordList: MutableLiveData<List<KeywordEntity>> = MutableLiveData()
    val localKeywordList: LiveData<List<KeywordEntity>> get() = _localKeywordList

    private val _checkKeywordList = MutableLiveData<Set<String>>(mutableSetOf())
    val checkKeywordList: LiveData<Set<String>> get() = _checkKeywordList

    fun getLocalKeywordList() {
        viewModelScope.launch {
            keywordRepository.getUserKeywords().collect { keywordList ->
                _localKeywordList.postValue(keywordList)
            }
        }
    }

    fun subscribeCheckedKeyword() {
        for (localKeyword in localKeywordList.value ?: emptyList()) {
            if (checkKeywordList.value?.contains(localKeyword.name) == true) {
                // 선택했던 데이터를 중첩해서 바꾸면 효율성이 떨어지고, 파이어베이스 구독에 문제가 생길 수 있으므로 구독 여부도 함께 체크
                if (!localKeyword.isSubscribe) {
                    viewModelScope.launch {
                        keywordRepository.updateUserKeywords(true, localKeyword.name)
                    }
                }
            } else {
                if (localKeyword.isSubscribe) {
                    viewModelScope.launch {
                        keywordRepository.updateUserKeywords(false, localKeyword.name)
                    }
                }
            }
        }
    }

    fun setAllKeywords(keyword: List<String>) {
        _checkKeywordList.value = keyword.toSet()
    }

    fun setCheckedKeywords(keyword: String) {
        _checkKeywordList.value = _checkKeywordList.value?.plus(keyword) ?: setOf(keyword)
    }

    fun removeCheckedKeywords(keyword: String) {
        _checkKeywordList.value = _checkKeywordList.value?.minus(keyword) ?: setOf()
    }
}
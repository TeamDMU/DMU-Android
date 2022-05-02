package com.dongyang.android.youdongknowme.ui.view.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/* 공지사항 뷰모델 */
class NoticeViewModel : ViewModel() {

    var title = "hoyaho"

    val isSearchMode : LiveData<Boolean> get() = _isSearchMode
    private val _isSearchMode = MutableLiveData(false)

    fun setSearchMode(value : Boolean) {
        _isSearchMode.postValue(value)
    }
}
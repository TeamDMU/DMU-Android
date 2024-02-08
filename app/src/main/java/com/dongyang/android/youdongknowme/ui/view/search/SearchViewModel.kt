package com.dongyang.android.youdongknowme.ui.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class SearchViewModel : BaseViewModel() {
    private val _searchContent: MutableLiveData<String> = MutableLiveData()

    private val _searchClearVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val searchClearVisibility: LiveData<Boolean> = _searchClearVisibility

    fun updateSearchContent(newContent: String) {
        _searchContent.value = newContent
    }

    fun validateSearchClearButtonVisibility() {
        _searchClearVisibility.value = _searchContent.value.isNullOrEmpty().not()
    }
}
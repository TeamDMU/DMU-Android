package com.dongyang.android.youdongknowme.ui.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class SearchViewModel : BaseViewModel() {
    private val _searchContent: MutableLiveData<String> = MutableLiveData()
    val searchContent: MutableLiveData<String> = _searchContent

    private val _searchValidation: MutableLiveData<Boolean> = MutableLiveData()
    val searchValidation: LiveData<Boolean> = _searchValidation

    fun validateSearchContent() {
        _searchValidation.value = !_searchContent.value.isNullOrBlank()
    }
}
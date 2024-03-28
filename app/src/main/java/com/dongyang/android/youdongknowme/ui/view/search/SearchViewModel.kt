package com.dongyang.android.youdongknowme.ui.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.repository.NoticeRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import kotlinx.coroutines.launch

class SearchViewModel(
    private val noticeRepository: NoticeRepository
) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _searchContent: MutableLiveData<String> = MutableLiveData()
    private val searchContent: LiveData<String> = _searchContent

    private val _searchClearVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val searchClearVisibility: LiveData<Boolean> get() = _searchClearVisibility

    private val _myDepartment: MutableLiveData<String> = MutableLiveData()
    val myDepartment: LiveData<String> = _myDepartment

    private val _searchNotices: MutableLiveData<List<Notice>> = MutableLiveData()
    val searchNotices: LiveData<List<Notice>> = _searchNotices

    private var searchNoticeCurrentPage = 1

    init {
        getUserDepartment()
    }

    private fun getUserDepartment() {
        val myDepartment = noticeRepository.getUserDepartment()
        _myDepartment.postValue(myDepartment)
    }

    fun updateSearchContent(newContent: String) {
        _searchContent.value = newContent
        _searchNotices.postValue(emptyList())
        searchNoticeCurrentPage = 1
        validateSearchClearButtonVisibility()
    }

    private fun validateSearchClearButtonVisibility() {
        _searchClearVisibility.value = _searchContent.value.isNullOrEmpty().not()
    }

    fun fetchSearchNotices() {
        if (_isLoading.value == true) {
            return
        }
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result =
                noticeRepository.fetchSearchNotices(
                    searchContent.value.toString(),
                    myDepartment.value.toString(),
                    searchNoticeCurrentPage
                )) {
                is NetworkResult.Success -> {
                    val updatedSearchResult = _searchNotices.value.orEmpty() + result.data
                    _searchNotices.postValue(updatedSearchResult)
                    _isError.postValue(false)
                    _isLoading.postValue(false)
                    searchNoticeCurrentPage++
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isError.postValue(true)
                    _isLoading.postValue(false)
                }
            }
        }
    }
}
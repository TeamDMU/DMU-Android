package com.dongyang.android.youdongknowme.ui.view.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.repository.NoticeRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import kotlinx.coroutines.launch

class NoticeViewModel(
    private val noticeRepository: NoticeRepository
) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _selectedTab: MutableLiveData<Event<NoticeTabType>> = MutableLiveData()
    val selectedTab: LiveData<Event<NoticeTabType>> = _selectedTab

    private val _universityNotices: MutableLiveData<List<Notice>?> = MutableLiveData()
    val universityNotices: LiveData<List<Notice>?> = _universityNotices

    private val _departmentNotices: MutableLiveData<List<Notice>?> = MutableLiveData()
    val departmentNotices: LiveData<List<Notice>?> = _departmentNotices

    init {
        updateSelectedTabType(NoticeTabType.SCHOOL)
        fetchUniversityNotices()
    }

    private fun updateSelectedTabType(tabType: NoticeTabType) {
        _selectedTab.value = Event(tabType)
    }

    fun fetchUniversityNotices() {
        val universityNotices = _universityNotices.value ?: emptyList()

        if (universityNotices.isEmpty()) {
            _isLoading.postValue(true)

            viewModelScope.launch {
                when (val result = noticeRepository.fetchUniversityNotices()) {
                    is NetworkResult.Success -> {
                        _universityNotices.value = result.data
                        _isError.postValue(false)
                        _isLoading.postValue(false)
                    }

                    is NetworkResult.Error -> {
                        handleError(result, _errorState)
                        _isError.postValue(true)
                        _isLoading.postValue(false)
                    }
                }
            }
        } else {
            _universityNotices.postValue(_universityNotices.value)
        }
    }

    fun fetchDepartmentNotices() {
        val departmentNotices = _departmentNotices.value ?: emptyList()

        if (departmentNotices.isEmpty()) {
            _isLoading.postValue(true)

            viewModelScope.launch {
                when (val result = noticeRepository.fetchDepartmentNotices("컴퓨터소프트웨어공학과")) {
                    is NetworkResult.Success -> {
                        _departmentNotices.value = result.data
                        _isError.postValue(false)
                        _isLoading.postValue(false)
                    }

                    is NetworkResult.Error -> {
                        handleError(result, _errorState)
                        _isError.postValue(true)
                        _isLoading.postValue(false)
                    }
                }
            }
        } else {
            _departmentNotices.postValue(_departmentNotices.value)
        }
    }
}
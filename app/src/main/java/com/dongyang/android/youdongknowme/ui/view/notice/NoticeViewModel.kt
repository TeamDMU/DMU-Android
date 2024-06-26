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
    private val noticeRepository: NoticeRepository,
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

    private var department: String
    private var universityNoticeCurrentPage = 1
    private var departmentNoticeCurrentPage = 1

    init {
        updateSelectedTabType(NoticeTabType.SCHOOL)
        fetchUniversityNotices()
        department = noticeRepository.getUserDepartment()
    }

    fun updateSelectedTabType(tabType: NoticeTabType) {
        _selectedTab.value = Event(tabType)
    }

    fun fetchUniversityNotices() {
        if (_isLoading.value == true) {
            return
        }
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result =
                noticeRepository.fetchUniversityNotices(universityNoticeCurrentPage)) {
                is NetworkResult.Success -> {
                    val updatedNotices = _universityNotices.value.orEmpty() + result.data
                    _universityNotices.postValue(updatedNotices)
                    _isError.postValue(false)
                    _isLoading.postValue(false)
                    universityNoticeCurrentPage++
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isError.postValue(true)
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun fetchDepartmentNotices() {
        if (_isLoading.value == true) {
            return
        }
        _isLoading.postValue(true)

        viewModelScope.launch {
            when (val result =
                noticeRepository.fetchDepartmentNotices(
                    departmentNoticeCurrentPage
                )) {
                is NetworkResult.Success -> {
                    val updatedNotices = _departmentNotices.value.orEmpty() + result.data
                    _departmentNotices.postValue(updatedNotices)
                    _isError.postValue(false)
                    _isLoading.postValue(false)
                    departmentNoticeCurrentPage++
                }

                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isError.postValue(true)
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun refreshNotices() {
        _isLoading.postValue(true)
        _selectedTab.value?.peekContent()?.let { currentTab ->
            when (currentTab) {
                NoticeTabType.SCHOOL -> viewModelScope.launch {
                    when (val result =
                        noticeRepository.fetchUniversityNotices(DEFAULT_REFRESH_PAGE)) {
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

                NoticeTabType.FACULTY -> viewModelScope.launch {
                    when (val result =
                        noticeRepository.fetchDepartmentNotices(
                            DEFAULT_REFRESH_PAGE
                        )) {
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
            }
        }
    }

    fun refreshIfChangedDepartment() {
        val currentDepartment = noticeRepository.getUserDepartment()

        if (department != currentDepartment) {
            department = currentDepartment
            refreshNotices()
        }
    }

    companion object {
        private const val DEFAULT_REFRESH_PAGE = 1
    }
}

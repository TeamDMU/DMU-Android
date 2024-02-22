package com.dongyang.android.youdongknowme.ui.view.notice

import CODE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.repository.NoticeRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import kotlinx.coroutines.launch

/* 공지사항 뷰모델 */
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

    private val _departmentCode: MutableLiveData<Int> = MutableLiveData()
    val departmentCode: LiveData<Int> = _departmentCode

    private val _universityNoticeList: MutableLiveData<List<Notice>> = MutableLiveData()

    private val _facultyNoticeList: MutableLiveData<List<Notice>> = MutableLiveData()

    private val _noticeList: MutableLiveData<List<Notice>> = MutableLiveData()
    val noticeList: LiveData<List<Notice>> = _noticeList

    init {
        updateSelectedTabType(NoticeTabType.SCHOOL)
    }

    fun updateSelectedTabType(tabType: NoticeTabType) {
        _selectedTab.value = Event(tabType)
        setDepartmentCode()
    }

    private fun setDepartmentCode() {
        if (selectedTab.value?.peekContent() == NoticeTabType.SCHOOL) {
            _departmentCode.value = CODE.SCHOOL_CODE
        } else {
            _departmentCode.value = noticeRepository.getDepartmentCode()
        }
        fetchNotices()
    }

    private fun fetchNotices() {
        // 비어있을 때만 새로 갱신
        val universityNoticeList = _universityNoticeList.value ?: emptyList()
        val facultyNoticeList = _facultyNoticeList.value ?: emptyList()

        if (universityNoticeList.isEmpty() or facultyNoticeList.isEmpty()) {
            _isLoading.postValue(true)

            viewModelScope.launch {
                when (val result =
                    noticeRepository.fetchNotices(departmentCode.value ?: DEFAULT_VALUE)) {
                    is NetworkResult.Success -> {
                        val noticeList = result.data
                        // 네트워크 호출을 줄이기 위해 학교, 학과별 리스트를 따로 보관
                        when (departmentCode.value) {
                            CODE.SCHOOL_CODE -> {
                                _universityNoticeList.value = noticeList
                                _noticeList.postValue(noticeList)
                            }

                            else -> {
                                _facultyNoticeList.value = noticeList
                                _noticeList.postValue(noticeList)
                            }
                        }
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
        // 비어있지 않을 때는 저장 데이터 바로 사용
        else {
            if (departmentCode.value == CODE.SCHOOL_CODE)
                _noticeList.postValue(_universityNoticeList.value)
            else
                _noticeList.postValue(_facultyNoticeList.value)
        }
    }

    fun refreshNotices() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = noticeRepository.fetchAllNotices()) {
                is NetworkResult.Success -> {
                    val noticeMap = result.data

                    _universityNoticeList.value = noticeMap["school"]
                    _facultyNoticeList.value = noticeMap["depart"]

                    when (departmentCode.value) {
                        CODE.SCHOOL_CODE -> {
                            _noticeList.postValue(noticeMap["school"])
                        }

                        else -> {
                            _noticeList.postValue(noticeMap["depart"])
                        }
                    }
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

    companion object {
        private const val DEFAULT_VALUE = 0
    }
}
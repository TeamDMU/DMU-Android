package com.dongyang.android.youdongknowme.ui.view.notice

import CODE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.repository.NoticeRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/* 공지사항 뷰모델 */
class NoticeViewModel(
    private val noticeRepository: NoticeRepository
) : BaseViewModel() {

    private val _isUniversityTab = MutableLiveData(true)
    val isUniversityTab: LiveData<Boolean> get() = _isUniversityTab

    private val _departmentCode: MutableLiveData<Int> = MutableLiveData()
    val departmentCode: LiveData<Int> get() = _departmentCode

    private val _isSearchMode = MutableLiveData(false)
    val isSearchMode: LiveData<Boolean> get() = _isSearchMode

    private val _universityNoticeList: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())

    private val _facultyNoticeList: MutableLiveData<List<Notice>> = MutableLiveData(emptyList())

    private val _noticeList: MutableLiveData<List<Notice>> = MutableLiveData()
    val noticeList: LiveData<List<Notice>> = _noticeList

    private val _unVisitedAlarmCount: MutableLiveData<Int> = MutableLiveData()
    val unVisitedAlarmCount: LiveData<Int> = _unVisitedAlarmCount

    // 선택한 탭에 대한 데이터 저장
    fun setTabMode(isUniversityMode: Boolean) {
        _isUniversityTab.value = isUniversityMode
    }

    // 선택 학부에 따라 보여지는 공지사항이 달라지게 설정
    fun setDepartmentCode() {
        if (isUniversityTab.value!!) { // 대학 공지사항 탭 클릭시 대학 공지사항이 보이게 설정
            _departmentCode.value = CODE.SCHOOL_CODE
        } else {
            _departmentCode.value = noticeRepository.getDepartmentCode()
        }
        fetchNotices()
    }

    // 검색 모드 활성화 상태 체크
    fun setSearchMode(value: Boolean) {
        _isSearchMode.postValue(value)
    }

    fun refreshNotices() {
        showLoading()
        viewModelScope.launch {
            when (val result = noticeRepository.fetchNotices(departmentCode.value!!)) {
                is NetworkResult.Success -> {
                    val noticeList = result.data
                    // 네트워크 호출을 줄이기 위해 학교, 학과별 리스트를 따로 보관
                    if (departmentCode.value!! == CODE.SCHOOL_CODE) {
                        _universityNoticeList.value = noticeList
                        _noticeList.postValue(noticeList)
                    } else {
                        _facultyNoticeList.value = noticeList
                        _noticeList.postValue(noticeList)
                    }
                    dismissLoading()
                }
                is NetworkResult.Error -> {
                    handleError(result)
                    dismissLoading()
                }
            }
        }
    }

    // 공지사항 리스트 호출
    private fun fetchNotices() {
        // 비어있을 때만 새로 갱신
        if (_universityNoticeList.value!!.isEmpty() or _facultyNoticeList.value!!.isEmpty()) {
            showLoading()

            viewModelScope.launch {
                when (val result = noticeRepository.fetchNotices(departmentCode.value!!)) {
                    is NetworkResult.Success -> {
                        val noticeList = result.data
                        // 네트워크 호출을 줄이기 위해 학교, 학과별 리스트를 따로 보관
                        if (departmentCode.value!! == CODE.SCHOOL_CODE) {
                            _universityNoticeList.value = noticeList
                            _noticeList.postValue(noticeList)
                        } else {
                            _facultyNoticeList.value = noticeList
                            _noticeList.postValue(noticeList)
                        }
                        dismissLoading()
                    }
                    is NetworkResult.Error -> {
                        handleError(result)
                        dismissLoading()
                    }
                }
            }
        }
        // 비어있지 않을 때는 저장 데이터 바로 사용
        else {
            if (departmentCode.value!! == CODE.SCHOOL_CODE) {
                _noticeList.postValue(_universityNoticeList.value!!)
            } else {
                _noticeList.postValue(_facultyNoticeList.value!!)
            }
        }
    }

    // 검색어가 포함된 공지사항 리스트 호출
    fun fetchSearchNotices(keyword: String) {
        showLoading()

        viewModelScope.launch {
            when (val result = noticeRepository.fetchSearchNotices(departmentCode.value!!, keyword)) {
                is NetworkResult.Success -> {
                    val searchList = result.data
                    _noticeList.postValue(searchList)
                }
                is NetworkResult.Error -> {
                    handleError(result)
                    dismissLoading()
                }
            }
        }
    }

    fun getUnVisitedAlarm() {
        viewModelScope.launch {
            noticeRepository.getUnVisitedAlarmCount().collect { count ->
                _unVisitedAlarmCount.postValue(count)
            }
        }
    }
}
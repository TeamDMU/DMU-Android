package com.dongyang.android.youdongknowme.ui.view.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.repository.NoticeRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import kotlinx.coroutines.launch

/* 공지사항 뷰모델 */
class NoticeViewModel(
    private val noticeRepository: NoticeRepository
) : BaseViewModel() {

    private val _isUniversityTab = MutableLiveData(true)
    val isUniversityTab : LiveData<Boolean> get() = _isUniversityTab

    private val _departmentCode = MutableLiveData(1)
    val departmentCode : LiveData<Int> get() = _departmentCode

    private val _isSearchMode = MutableLiveData(false)
    val isSearchMode : LiveData<Boolean> get() = _isSearchMode

    private val _noticeList : MutableLiveData<List<Notice>> = MutableLiveData()
    val noticeList : LiveData<List<Notice>> = _noticeList

    private val _searchList : MutableLiveData<List<Notice>> = MutableLiveData()
    val searchList : LiveData<List<Notice>> = _searchList

    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> = _errorState

    // 선택한 탭에 대한 데이터 저장
    fun setTabMode(isUniversityMode: Boolean) {
        _isUniversityTab.postValue(isUniversityMode)
    }

    // 선택 학부에 따라 보여지는 공지사항이 달라지게 설정
    fun setDepartmentCode() {
        if(isUniversityTab.value!!) { // 대학 공지사항 탭 클릭시 대학 공지사항이 보이게 설정
            _departmentCode.postValue(CODE.SCHOOL_CODE)
        } else {
            _departmentCode.postValue(noticeRepository.getDepartmentCode())
        }
    }

    // 검색 모드 활성화 상태 체크
    fun setSearchMode(value : Boolean) {
        _isSearchMode.postValue(value)
    }

    // 공지사항 리스트 호출
    fun getNoticeList() {
        try {
            viewModelScope.launch(connectionHandler) {
                val response = noticeRepository.getNoticeList(departmentCode.value!!)
                if(response.isSuccessful) {
                    val noticeList = response.body()
                    _noticeList.postValue(noticeList!!)
                } else {
                    _errorState.postValue(ERROR_NOTICE)
                }
            }
        } catch (e: Exception){
            _errorState.postValue(ERROR_NOTICE)
        }
    }

    // 검색어가 포함된 공지사항 리스트 호출
    fun getNoticeSearchList(keyword : String) {
        try {
            viewModelScope.launch(connectionHandler) {
                val response = noticeRepository.getNoticeSearchList(departmentCode.value!!, keyword)
                if(response.isSuccessful) {
                    val searchList = response.body()
                    _searchList.postValue(searchList!!)
                } else {
                    _errorState.postValue(ERROR_NOTICE)
                }
            }
        } catch (e: Exception){
            _errorState.postValue(ERROR_NOTICE)
        }
    }

    companion object {
        const val ERROR_NOTICE = R.string.error_notice
    }

}
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

    val isSearchMode : LiveData<Boolean> get() = _isSearchMode
    private val _isSearchMode = MutableLiveData(false)

    private val _noticeList : MutableLiveData<List<Notice>> = MutableLiveData()
    val noticeList : LiveData<List<Notice>> = _noticeList

    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> = _errorState

    // 검색 모드 활성화 상태 체크
    fun setSearchMode(value : Boolean) {
        _isSearchMode.postValue(value)
    }

    // 공지사항 리스트 호출
    fun getNoticeList() {
        try {
            viewModelScope.launch(connectionHandler) {
                val response = noticeRepository.getNoticeList()
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

    companion object {
        const val ERROR_NOTICE = R.string.error_notice
    }

}
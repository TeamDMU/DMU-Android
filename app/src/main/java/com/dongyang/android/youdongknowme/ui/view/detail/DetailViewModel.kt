package com.dongyang.android.youdongknowme.ui.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import com.dongyang.android.youdongknowme.data.repository.DetailRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.util.log
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    private val _noticeDetail : MutableLiveData<NoticeDetail> = MutableLiveData()
    val noticeDetail : LiveData<NoticeDetail> = _noticeDetail

    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> = _errorState

    // 공지사항 리스트 호출
    fun getNoticeList(code : Int, num : Int) {
        try {
            viewModelScope.launch(connectionHandler) {
                val response = detailRepository.getNoticeDetail(code, num)
                if(response.isSuccessful) {
                    val noticeDetail = response.body()
                    _noticeDetail.postValue(noticeDetail!!)
                } else {
                    _errorState.postValue(ERROR_NOTICE)
                }
            }
        } catch (e: Exception){
            log("에러 발생")
            _errorState.postValue(ERROR_NOTICE)
        }
    }

    companion object {
        const val ERROR_NOTICE = R.string.error_notice
    }
}
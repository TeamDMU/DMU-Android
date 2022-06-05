package com.dongyang.android.youdongknowme.ui.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeFileUrl
import com.dongyang.android.youdongknowme.data.repository.DetailRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.util.logd
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    private val _num: MutableLiveData<Int> = MutableLiveData()
    private val _code: MutableLiveData<Int> = MutableLiveData()

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> get() = _title
    private val _writer: MutableLiveData<String> = MutableLiveData()
    val writer: LiveData<String> get() = _writer
    private val _date: MutableLiveData<String> = MutableLiveData()
    val date: LiveData<String> get() = _date
    private val _content: MutableLiveData<String> = MutableLiveData()
    val content: LiveData<String> get() = _content
    private val _imgUrl: MutableLiveData<List<String>> = MutableLiveData()
    val imgUrl: LiveData<List<String>> get() = _imgUrl
    private val _fileUrl: MutableLiveData<List<NoticeFileUrl>> = MutableLiveData()
    val fileUrl: LiveData<List<NoticeFileUrl>> get() = _fileUrl

    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> = _errorState

    // 학과 코드, 게시글 번호 저장
    fun setNoticeDetailInfo(code: Int, num: Int) {
        _code.value = code
        _num.value = num
    }

    // 공지사항 리스트 호출
    fun getNoticeDetail() {
        _isLoading.postValue(true)

        val departCode = _code.value!!
        val boardNum = _num.value!!

        try {
            viewModelScope.launch(connectionHandler) {
                val response = detailRepository.getNoticeDetail(departCode, boardNum)
                if (response.isSuccessful) {
                    val noticeDetail = response.body()
                    _title.postValue(noticeDetail?.title)
                    _writer.postValue(noticeDetail?.writer)
                    _date.postValue(noticeDetail?.date)
                    _content.postValue(noticeDetail?.content)
                    _imgUrl.postValue(noticeDetail?.imgUrl)
                    _fileUrl.postValue(noticeDetail?.fileUrl)
                } else {
                    _errorState.postValue(ERROR_NOTICE)
                }
                _isLoading.postValue(false)
            }
        } catch (e: Exception) {
            _errorState.postValue(ERROR_NOTICE)
            _isLoading.postValue(false)
        }
    }

    companion object {
        const val ERROR_NOTICE = R.string.error_notice
    }
}
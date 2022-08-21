package com.dongyang.android.youdongknowme.ui.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.NoticeFileUrl
import com.dongyang.android.youdongknowme.data.repository.DetailRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading

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

    // 학과 코드, 게시글 번호 저장
    fun setNoticeDetailInfo(code: Int, num: Int) {
        _code.value = code
        _num.value = num
    }

    // 공지사항 리스트 호출
    fun fetchNoticeDetail() {
        _isLoading.postValue(true)

        val departCode = _code.value ?: DEFAULT_VALUE
        val boardNum = _num.value ?: DEFAULT_VALUE

        viewModelScope.launch {
            when (val result = detailRepository.fetchNoticeDetail(departCode, boardNum)) {
                is NetworkResult.Success -> {
                    val noticeDetail = result.data
                    _title.postValue(noticeDetail.title)
                    _writer.postValue(noticeDetail.writer)
                    _date.postValue(noticeDetail.date)
                    _content.postValue(noticeDetail.content)
                    _imgUrl.postValue(noticeDetail.imgUrl)
                    _fileUrl.postValue(noticeDetail.fileUrl)
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
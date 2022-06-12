package com.dongyang.android.youdongknowme.standard.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongyang.android.youdongknowme.R
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    // 로딩 유무 확인
    protected val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // 서버 연결 확인
    protected val _errorConnectionState: MutableLiveData<Int> = MutableLiveData()
    val errorConnectionState: LiveData<Int> = _errorConnectionState

    open val connectionHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _isLoading.postValue(false)
        _errorConnectionState.postValue(ERROR_NETWORK)
    }

    companion object {
        const val ERROR_NETWORK = R.string.error_network
    }
}
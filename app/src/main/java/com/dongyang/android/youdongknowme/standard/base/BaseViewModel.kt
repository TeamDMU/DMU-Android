package com.dongyang.android.youdongknowme.standard.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongyang.android.youdongknowme.R
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    // 서버 연결 확인
    private val _errorConnectionState: MutableLiveData<Int> = MutableLiveData()
    val errorConnectionState: LiveData<Int> = _errorConnectionState

    val connectionHandler = CoroutineExceptionHandler { _, _ ->
        _errorConnectionState.postValue(ERROR_NETWORK)
    }

    companion object {
        const val ERROR_NETWORK = R.string.error_network
    }
}
package com.dongyang.android.youdongknowme.standard.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.standard.network.NetworkError
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel() {

    // 서버 연결 확인
    private val _errorState: MutableLiveData<Int> = MutableLiveData()
    val errorState: LiveData<Int> = _errorState

    protected fun handleError(result: NetworkResult.Error) {
        when (result.errorType) {
            is NetworkError.Unknown -> {
                _errorState.postValue(ERROR_UNKNOWN)
            }
            is NetworkError.Timeout -> {
                _errorState.postValue(ERROR_TIMEOUT)
            }
            is NetworkError.InternalServer -> {
                _errorState.postValue(ERROR_INTERNAL_SERVER)
            }
            is NetworkError.BadRequest -> {
                val code = (result.errorType).code
                val msg = (result.errorType).message
                Log.w("Exception", "BadReuqest :: $code - $msg")
                _errorState.postValue(ERROR_BAD_REQUEST)
            }
        }
    }

    companion object {
        private const val ERROR_UNKNOWN = R.string.error_unknown
        private const val ERROR_TIMEOUT = R.string.error_timeout
        private const val ERROR_INTERNAL_SERVER = R.string.error_internal_server
        private const val ERROR_BAD_REQUEST = R.string.error_bad_request
    }
}
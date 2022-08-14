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

    protected fun handleError(result: NetworkResult.Error, errorState: MutableLiveData<Int>) {
        when (result.errorType) {
            is NetworkError.Unknown -> {
                errorState.postValue(ERROR_UNKNOWN)
            }
            is NetworkError.Timeout -> {
                errorState.postValue(ERROR_TIMEOUT)
            }
            is NetworkError.InternalServer -> {
                errorState.postValue(ERROR_INTERNAL_SERVER)
            }
            is NetworkError.BadRequest -> {
                val code = (result.errorType).code
                val msg = (result.errorType).message
                Log.w("Exception", "BadReuqest :: $code - $msg")
                errorState.postValue(ERROR_BAD_REQUEST)
            }
        }
    }

    companion object {
        const val ERROR_UNKNOWN = R.string.error_unknown
        const val ERROR_TIMEOUT = R.string.error_timeout
        const val ERROR_INTERNAL_SERVER = R.string.error_internal_server
        const val ERROR_BAD_REQUEST = R.string.error_bad_request
    }
}
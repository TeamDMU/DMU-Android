package com.dongyang.android.youdongknowme.standard.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.standard.network.NetworkError
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event

abstract class BaseViewModel : ViewModel() {

    protected fun handleError(
        result: NetworkResult.Error,
        errorState: MutableLiveData<Event<Int>>
    ) {
        when (result.errorType) {
            is NetworkError.Unknown -> {
                errorState.postValue(Event(ERROR_UNKNOWN))
            }
            is NetworkError.Timeout -> {
                errorState.postValue(Event(ERROR_TIMEOUT))
            }
            is NetworkError.InternalServer -> {
                errorState.postValue(Event(ERROR_INTERNAL_SERVER))
            }
            is NetworkError.BadRequest -> {
                errorState.postValue(Event(ERROR_BAD_REQUEST))
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
package com.dongyang.android.youdongknowme.standard.network

sealed class NetworkResult<out T>() {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error(val errorType: NetworkError) : NetworkResult<Nothing>()
}

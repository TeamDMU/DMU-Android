package com.dongyang.android.youdongknowme.standard.network

sealed class NetworkError {
    object Unknown : NetworkError()
    object Timeout : NetworkError()
    object InternalServer : NetworkError()
    class BadRequest(val code: Int, val message: String) : NetworkError()
}

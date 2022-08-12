package com.dongyang.android.youdongknowme.standard.network

import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException

class ErrorResponseHandler {

    fun getError(exception: Throwable): NetworkError {
        return when (exception) {
            is SocketTimeoutException -> NetworkError.Timeout
            is HttpException -> {
                when (exception.code()) {
                    in 500..599 -> NetworkError.InternalServer
                    in 400..499 -> {
                        val code = exception.code()
                        val message = extractErrorMessage(exception.response())
                        NetworkError.BadRequest(code, message)
                    }
                    else -> NetworkError.Unknown
                }
            }
            else -> NetworkError.Unknown
        }
    }

    private fun extractErrorMessage(response: Response<*>?): String {
        return response?.message().orEmpty()
    }
}
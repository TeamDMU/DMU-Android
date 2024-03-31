package com.dongyang.android.youdongknowme.data.remote.service

import android.media.session.MediaSession
import com.dongyang.android.youdongknowme.data.remote.entity.Token
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("token/v1/dmu/initToken")
    suspend fun setInitToken(
        @Body data: Token,
    )
}
package com.dongyang.android.youdongknowme.standard.network

import com.dongyang.android.youdongknowme.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObject {
    private const val TIME_OUT_COUNT: Long = 10

    fun getNetwork(): Retrofit {
        val baseUrl = "http://43.200.194.238:8080/"

        val baseInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url

            val url = originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            request.url(url)
            chain.proceed(request.build())
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT_COUNT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_COUNT, TimeUnit.SECONDS)
            .addInterceptor(baseInterceptor)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }).build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
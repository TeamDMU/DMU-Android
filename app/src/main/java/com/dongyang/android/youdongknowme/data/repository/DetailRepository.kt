package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.remote.entity.NoticeDetail
import com.dongyang.android.youdongknowme.data.remote.service.DetailService
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import retrofit2.Response

class DetailRepository {
    suspend fun getNoticeDetail(code : Int, num : Int) : Response<NoticeDetail> {
        return RetrofitObject.getNetwork().create(DetailService::class.java).getNoticeDetail(code, num)
    }
}
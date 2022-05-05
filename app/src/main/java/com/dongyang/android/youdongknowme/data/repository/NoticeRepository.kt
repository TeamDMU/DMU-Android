package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.service.NoticeService
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import retrofit2.Response

class NoticeRepository {
    suspend fun getNoticeList() : Response<List<Notice>> {
        return RetrofitObject.getNetwork().create(NoticeService::class.java).getList()
    }

    suspend fun getNoticeSearchList(keyword : String) : Response<List<Notice>> {
        return RetrofitObject.getNetwork().create(NoticeService::class.java).getSearchList(keyword)
    }
}
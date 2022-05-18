package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.data.remote.service.NoticeService
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import retrofit2.Response

class NoticeRepository {
    suspend fun getNoticeList(code : Int) : Response<List<Notice>> {
        return RetrofitObject.getNetwork().create(NoticeService::class.java).getList(code)
    }

    suspend fun getNoticeSearchList(code : Int, keyword : String) : Response<List<Notice>> {
        return RetrofitObject.getNetwork().create(NoticeService::class.java).getSearchList(code, keyword)
    }

    fun getDepartmentCode(): Int? = SharedPreference.getCode()
}
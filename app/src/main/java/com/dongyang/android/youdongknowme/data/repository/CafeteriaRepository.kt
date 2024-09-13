package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.remote.service.CafeteriaService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import com.dongyang.android.youdongknowme.standard.util.Weekdays

class CafeteriaRepository(
    private val errorResponseHandler: ErrorResponseHandler,
) {
    suspend fun fetchMenuList(): NetworkResult<List<Cafeteria>> {
        return try {
            val response =
                RetrofitObject.getNetwork().create(CafeteriaService::class.java).getMenuList()
            NetworkResult.Success(response)
        } catch (exception: Exception) {
            val error = errorResponseHandler.getError(exception)
            NetworkResult.Error(error)
        }
    }

    fun fetchDaysMenu(todayDay: Weekdays): List<DaysMenu> {
        return DaysMenu.values().filter { menu ->
            menu.operatingDays.contains(todayDay)
        }
    }

    enum class DaysMenu(
        val menuNameKr: String,
        val price: Int,
        val operatingDays: List<Weekdays>,
    ) {
        PORKCUTLET("돈까스", 5_000, Weekdays.values().toList()),
        CHEESE_PORKCUTLET("치즈돈까스", 5_500, Weekdays.values().toList()),
        SWEET_POTATO_CHEESE_PORKCUTLET("고구마치즈돈까스", 6_000, Weekdays.values().toList()),
        RAMEN("라면", 3_500, Weekdays.values().toList()),
        CHEESE_RAMEN("치즈라면", 4_000, Weekdays.values().toList()),
        SEAFOOD_RAMEN("해물라면", 4_500, Weekdays.values().toList()),
        SPAM_KIMCHI_FRIED_RICE("스팸김치볶음밥", 4_900, listOf(Weekdays.MONDAY, Weekdays.TUESDAY)),
        HOT_CHICKEN_MAYO_RICE("불닭마요덮밥", 4_500, listOf(Weekdays.WEDNESDAY, Weekdays.THURSDAY)),
        CHICKEN_MAYO_RICE("치킨마요덮밥", 4_500, listOf(Weekdays.WEDNESDAY, Weekdays.THURSDAY)),
        OMELET_RICE("오므라이스", 5_500, listOf(Weekdays.FRIDAY));
    }
}
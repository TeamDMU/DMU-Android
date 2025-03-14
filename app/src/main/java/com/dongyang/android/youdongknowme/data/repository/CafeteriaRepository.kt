package com.dongyang.android.youdongknowme.data.repository

import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.remote.service.CafeteriaService
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import com.dongyang.android.youdongknowme.standard.util.Weekdays

class CafeteriaRepository(
    private val errorResponseHandler: ErrorResponseHandler
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

    fun fetchDaysMenus(todayDay: Weekdays): List<DaysMenu> {
        return DaysMenu.values().filter { menu ->
            menu.operatingDays.contains(todayDay)
        }
    }

    enum class DaysMenu(
        val menuNameKr: String,
        val price: Int,
        val operatingDays: List<Weekdays>,
    ) {
        SPAM_KIMCHI_FRIED_RICE("스팸 김치 볶음밥", 4_900, listOf(Weekdays.MONDAY, Weekdays.TUESDAY)),
        CHICKEN_MAYO_RICE("치킨 마요 덮밥", 4_900, listOf(Weekdays.WEDNESDAY)),
        SPICY_MAYO_RICE("불닭 마요 덮밥", 4_900, listOf(Weekdays.WEDNESDAY)),
        PORK_BELLY_RICE("삼겹살 덮밥", 5_500, listOf(Weekdays.THURSDAY)),
        JANGJORIM_BUTTER_RICE("장조림 버터 비빔밥", 4_500, listOf(Weekdays.FRIDAY)),

        RAMEN("라면", 3_500, Weekdays.values().toList()),
        CHEESE_RAMEN("치즈 라면", 4_000, Weekdays.values().toList()),
        SEAFOOD_RAMEN("해물짬뽕 라면", 4_500, Weekdays.values().toList()),

        CHAPAGETTI("짜파게티", 3_500, Weekdays.values().toList()),
        CHAPAGETTI_EGG_CHEESE("짜계치", 4_000, Weekdays.values().toList()),
        SPICY_RAMEN("불닭볶음면", 3_500, Weekdays.values().toList()),
        CARBONARA_SPICY_RAMEN("까르보 불닭볶음면", 3_800, Weekdays.values().toList()),
        CHEESE_SPICY_RAMEN("치즈 불닭볶음면", 4_000, Weekdays.values().toList()),

        PORK_CUTLET("돈까스", 5_000, Weekdays.values().toList()),
        CHEESE_PORK_CUTLET("치즈 돈까스", 5_500, Weekdays.values().toList()),
        BRISKET_CHICKEN_CUTLET("통가슴살 치킨까스", 5_200, Weekdays.values().toList()),
        SWEET_POTATO_CHEESE_PORK_CUTLET("고구마 치즈 돈까스", 6_000, Weekdays.values().toList()),
        HOMEMADE_KING_PORK_CUTLET("수제 왕 돈까스", 6_000, Weekdays.values().toList()),
    }
}
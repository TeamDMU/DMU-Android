package com.dongyang.android.youdongknowme.ui.view.schedule


import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.data.remote.entity.Schedule


/* 학사 일정 뷰모델 */
class ScheduleViewModel : BaseViewModel() {
    val testCode = listOf(Schedule(0,"22.03.31(목) ~ 22.04.06(수)", "1차 강의 평가"),
        Schedule(1,"22.04.05(화) ~ 22.04.06(수)", "등록금 분할납부(3회차)"),
        Schedule(2,"22.04.21(목) ~ 22.04.27(수)", "중간고사"),
        Schedule(3,"22.04.30(토) ~ 22.04.30(토)", "학기개시 60일 경과"))

    val testCode2 = listOf(Schedule(4,"05.01 (일)", "근로자의 날"),
        Schedule(5,"05.03 (화) ~ 05.04 (수)", "등록금 분할납부(4회차)"),
        Schedule(6,"05.08 (일)", "부처님 오신날"),
        Schedule(7,"05.11 (수) ~ 05.13 (금)", "3년제 학과 수업연한 연장 신청"))
}
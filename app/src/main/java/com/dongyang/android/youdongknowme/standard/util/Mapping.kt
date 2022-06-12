package com.dongyang.android.youdongknowme.standard.util

fun mapKeywordEnglishToKorean(english: String): String {
    return when (english) {
        "exam" -> "시험"
        "course" -> "수강"
        "lecture" -> "특강"
        "season" -> "계절학기"
        "scholarship" -> "장학"
        "kosaf" -> "국가장학"
        "tuition" -> "등록"
        "leave" -> "휴학"
        "return" -> "복학"
        "graduation" -> "졸업"
        "transfer" -> "전과"
        "drop" -> "학기포기"
        "recruitment" -> "채용"
        "contest" -> "공모전"
        "field" -> "현장실습"
        "competition" -> "대회"
        "service" -> "봉사"
        "dormitory" -> "기숙사"
        "corona" -> "코로나19"
        "club" -> "동아리"
        else -> throw IllegalArgumentException("올바른 타입이 아닙니다.")
    }
}

fun mapDepartmentCodeToKorean(code: Int): String {
    return when (code) {
        CODE.SCHOOL_CODE -> "학교"
        CODE.MECHANICAL_ENGINE_CODE -> "기계공학과"
        CODE.MECHANICAL_DESIGN_CODE -> "기계설계공학과"
        CODE.AUTOMATION_ENGINE_CODE -> "자동화공학과"
        CODE.ROBOT_ENGINE_CODE -> "로봇공학과"
        CODE.COMPUTER_SOFTWARE_ENGINE_CODE -> "컴퓨터소프트웨어공학과"
        CODE.COMPUTER_INFO_ENGINE_CODE -> "컴퓨터정보공학과"
        CODE.ARTIFICIAL_ENGINE_CODE -> "인공지능소프트웨어공학과"
        CODE.ELECTRICAL_ENGINE_CODE -> "전기공학과"
        CODE.INFO_ELECTRONIC_ENGINE_CODE -> "정보전자공학과"
        CODE.SEMICONDUCTOR_ENGINE_CODE -> "반도체전자공학과"
        CODE.INFO_COMMUNICATION_ENGINE_CODE -> "정보통신공학과"
        CODE.BIOCHEMICAL_ENGINE_CODE -> "생명화학공학과"
        CODE.BIO_CONVERGENCE_ENGINE_CODE -> "바이오융합공학과"
        CODE.ARCHITECTURE_CODE -> "건축과"
        CODE.INTERIOR_DESIGN_CODE -> "실내건축디자인과"
        CODE.VISUAL_DESIGN_CODE -> "시각디자인과"
        CODE.BUSINESS_ADMINISTRATION_CODE -> "경영학과"
        CODE.TAX_ACCOUNTING_CODE -> "세무회계학과"
        CODE.DISTRIBUTION_MARKETING_CODE -> "유통마케팅학과"
        CODE.HOTEL_TOURISM_CODE -> "호텔관광학과"
        CODE.MANAGEMENT_INFORMATION_CODE -> "경영정보학과"
        CODE.BIG_DATA_MANAGEMENT_CODE -> "빅데이터경영과"
        else -> throw IllegalArgumentException("올바른 타입이 아닙니다.")
    }
}

fun mapDepartmentKoreanToCode(department: String): Int {
    return when (department) {
        "동양미래대학교" -> CODE.SCHOOL_CODE
        "기계공학과" -> CODE.MECHANICAL_ENGINE_CODE
        "기계설계공학과" -> CODE.MECHANICAL_DESIGN_CODE
        "자동화공학과" -> CODE.AUTOMATION_ENGINE_CODE
        "로봇공학과" -> CODE.ROBOT_ENGINE_CODE
        "컴퓨터소프트웨어공학과" -> CODE.COMPUTER_SOFTWARE_ENGINE_CODE
        "컴퓨터정보공학과" -> CODE.COMPUTER_INFO_ENGINE_CODE
        "인공지능소프트웨어공학과" -> CODE.ARTIFICIAL_ENGINE_CODE
        "전기공학과" -> CODE.ELECTRICAL_ENGINE_CODE
        "정보전자공학과" -> CODE.INFO_ELECTRONIC_ENGINE_CODE
        "반도체전자공학과" -> CODE.SEMICONDUCTOR_ENGINE_CODE
        "정보통신공학과" -> CODE.INFO_COMMUNICATION_ENGINE_CODE
        "생명화학공학과" -> CODE.BIOCHEMICAL_ENGINE_CODE
        "바이오융합공학과" -> CODE.BIO_CONVERGENCE_ENGINE_CODE
        "건축과" -> CODE.ARCHITECTURE_CODE
        "실내건축디자인과" -> CODE.INTERIOR_DESIGN_CODE
        "시각디자인과" -> CODE.VISUAL_DESIGN_CODE
        "경영학과" -> CODE.BUSINESS_ADMINISTRATION_CODE
        "세무회계학과" -> CODE.TAX_ACCOUNTING_CODE
        "유통마케팅학과" -> CODE.DISTRIBUTION_MARKETING_CODE
        "호텔관광학과" -> CODE.HOTEL_TOURISM_CODE
        "경영정보학과" -> CODE.MANAGEMENT_INFORMATION_CODE
        "빅데이터경영과" -> CODE.BIG_DATA_MANAGEMENT_CODE
        else -> throw IllegalArgumentException("올바른 타입이 아닙니다.")
    }
}
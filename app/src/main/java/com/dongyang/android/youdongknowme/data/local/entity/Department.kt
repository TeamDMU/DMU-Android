package com.dongyang.android.youdongknowme.data.local.entity

sealed class Department(
    var name: String,
    var code: Int
) {
    object Mechanical : Department("기계공학과", CODE.MECHANICAL_ENGINE_CODE)
    object MechanicalDesign : Department("기계설계공학과", CODE.MECHANICAL_DESIGN_CODE)
    object Electrical : Department("전기공학과", CODE.ELECTRICAL_ENGINE_CODE)
    object InfoElectrical : Department("정보전자공학과", CODE.INFO_ELECTRONIC_ENGINE_CODE)
    object Semiconductor : Department("반도체전자공학과", CODE.SEMICONDUCTOR_ENGINE_CODE)
    object InfoCommunication : Department("정보통신공학과", CODE.INFO_COMMUNICATION_ENGINE_CODE)
    object ComputerSoftware : Department("컴퓨터소프트웨어공학과", CODE.COMPUTER_SOFTWARE_ENGINE_CODE)
    object ComputerInfo : Department("컴퓨터정보공학과", CODE.COMPUTER_INFO_ENGINE_CODE)
    object Artificial : Department("인공지능공학과", CODE.ARTIFICIAL_ENGINE_CODE)
    object Biochemical : Department("생명화학공학과", CODE.BIOCHEMICAL_ENGINE_CODE)
    object BioConvergence : Department("바이오융합공학과", CODE.BIO_CONVERGENCE_ENGINE_CODE)
    object Architecture : Department("건축과", CODE.ARCHITECTURE_CODE)
    object InteriorDesign : Department("실내건축디자인학과", CODE.INTERIOR_DESIGN_CODE)
    object VisualDesign : Department("시각디자인학과", CODE.VISUAL_DESIGN_CODE)
    object Business : Department("경영학과", CODE.BUSINESS_ADMINISTRATION_CODE)
    object TaxAccounting : Department("세무회계학과", CODE.TAX_ACCOUNTING_CODE)
    object DistributionMarketing : Department("유통마케팅학과", CODE.DISTRIBUTION_MARKETING_CODE)
    object HotelTourism : Department("호텔관광학과", CODE.HOTEL_TOURISM_CODE)
    object ManagementInformation : Department("경영정보학과", CODE.MANAGEMENT_INFORMATION_CODE)
    object BigDataManagement : Department("빅데이터경영과", CODE.BIG_DATA_MANAGEMENT_CODE)
    object Robot : Department("로봇공학과", CODE.ROBOT_ENGINE_CODE)
    object Automation : Department("자동화공학과", CODE.AUTOMATION_ENGINE_CODE)

    companion object {
        fun getDepartment(department: String): Department {
            return when (department) {
                "경영학과" -> Business
                "경영정보학과" -> ManagementInformation
                "기계공학과" -> Mechanical
                "기계설계공학과" -> MechanicalDesign
                "전기공학과" -> Electrical
                "정보전자공학과" -> InfoElectrical
                "반도체전자공학과" -> Semiconductor
                "정보통신공학과" -> InfoCommunication
                "컴퓨터소프트웨어공학과" -> ComputerSoftware
                "컴퓨터정보공학과" -> ComputerInfo
                "인공지능공학과" -> Artificial
                "생명화학공학과" -> Biochemical
                "바이오융합공학과" -> BioConvergence
                "건축과" -> Architecture
                "실내건축디자인학과" -> InteriorDesign
                "시각디자인학과" -> VisualDesign
                "세무회계학과" -> TaxAccounting
                "유통마케팅학과" -> DistributionMarketing
                "호텔관광학과" -> HotelTourism
                "빅데이터경영학과" -> BigDataManagement
                "로봇공학과" -> Robot
                "자동화공학과" -> Automation
                else -> throw IllegalArgumentException("올바른 타입이 아닙니다.")
            }
        }

        fun getDepartmentList(): ArrayList<Department> {
            return arrayListOf(
                Mechanical,
                MechanicalDesign,
                Electrical,
                InfoElectrical,
                Semiconductor,
                InfoCommunication,
                ComputerSoftware,
                ComputerInfo,
                Artificial,
                Biochemical,
                BioConvergence,
                Architecture,
                InteriorDesign,
                VisualDesign,
                Business,
                TaxAccounting,
                DistributionMarketing,
                HotelTourism,
                ManagementInformation,
                BigDataManagement
            )
        }
    }

}
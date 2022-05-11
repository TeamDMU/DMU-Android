package com.dongyang.android.youdongknowme.ui.view.depart

import com.dongyang.android.youdongknowme.data.repository.DepartRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class DepartViewModel(private val departRepository: DepartRepository) : BaseViewModel() {

    fun setDepartment(department: String) {
        departRepository.setDepartment(department)
    }
}
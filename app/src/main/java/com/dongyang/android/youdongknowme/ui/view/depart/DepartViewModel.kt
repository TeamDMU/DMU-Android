package com.dongyang.android.youdongknowme.ui.view.depart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.data.repository.DepartRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class DepartViewModel(private val departRepository: DepartRepository) : BaseViewModel() {

    private val _selectDepartPosition = MutableLiveData(-1)
    val selectDepartPosition : LiveData<Int> get() = _selectDepartPosition

    fun setDepartment(department: String) {
        departRepository.setDepartment(department)
    }

    fun setSelectPosition(position : Int) {
        _selectDepartPosition.postValue(position)
    }
}
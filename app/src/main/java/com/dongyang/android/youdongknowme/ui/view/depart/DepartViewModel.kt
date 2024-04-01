package com.dongyang.android.youdongknowme.ui.view.depart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dongyang.android.youdongknowme.data.repository.DepartRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel

class DepartViewModel(private val departRepository: DepartRepository) : BaseViewModel() {
    private val _myDepartment: MutableLiveData<String> = MutableLiveData()
    val myDepartment: LiveData<String> get() = _myDepartment

    private val _selectDepartPosition = MutableLiveData(-1)
    val selectDepartPosition: LiveData<Int> get() = _selectDepartPosition
    init {
        getUserDepartment()
    }

    private fun getUserDepartment() {
        val myDepartment = departRepository.getUserDepartment()
        _myDepartment.postValue(myDepartment)
    }

    fun setDepartment(department: String) {
        departRepository.setDepartment(department)
    }

    fun setSelectPosition(position: Int) {
        _selectDepartPosition.postValue(position)
    }
}
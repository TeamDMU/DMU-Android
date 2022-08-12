package com.dongyang.android.youdongknowme.ui.view.cafeteria

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.repository.CafeteriaRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import kotlinx.coroutines.launch
import java.time.LocalDate

class CafeteriaViewModel(private val cafeteriaRepository: CafeteriaRepository) : BaseViewModel() {

    private val _selectedDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _menuList: MutableLiveData<List<Cafeteria>> = MutableLiveData()
    val menuList: LiveData<List<Cafeteria>> = _menuList

    init {
        fetchCafeteria()
    }

    fun updateSelectedDate(selectedDate: LocalDate) {
        _selectedDate.postValue(selectedDate)
    }

    private fun fetchCafeteria() {
        viewModelScope.launch {
            showLoading()
            when (val result = cafeteriaRepository.fetchMenuList()) {
                is NetworkResult.Success -> {
                    val menuList = result.data
                    _menuList.postValue(menuList)
                    dismissLoading()
                }
                is NetworkResult.Error -> {
                    handleError(result)
                    dismissLoading()
                }
            }
        }
    }
}

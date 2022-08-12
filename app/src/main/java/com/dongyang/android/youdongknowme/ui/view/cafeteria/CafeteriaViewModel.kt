package com.dongyang.android.youdongknowme.ui.view.cafeteria

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.repository.CafeteriaRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.ResourceProvider
import kotlinx.coroutines.launch
import java.time.LocalDate

class CafeteriaViewModel(
    private val cafeteriaRepository: CafeteriaRepository,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel() {

    private val _selectedDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _cafeteriaList: MutableLiveData<List<Cafeteria>> = MutableLiveData()

    private val _stuMenuList: MutableLiveData<List<String>> = MutableLiveData()
    val stuMenuList: LiveData<List<String>> = _stuMenuList

    private val _eduMenuList: MutableLiveData<List<String>> = MutableLiveData()
    val eduMenuList: LiveData<List<String>> = _eduMenuList

    init {
        fetchCafeteria()
        updateSelectedDate(LocalDate.now())
    }

    private fun fetchCafeteria() {
        viewModelScope.launch {
            showLoading()
            when (val result = cafeteriaRepository.fetchMenuList()) {
                is NetworkResult.Success -> {
                    val menuList = result.data
                    _cafeteriaList.postValue(menuList)
                    dismissLoading()
                }
                is NetworkResult.Error -> {
                    handleError(result)
                    dismissLoading()
                }
            }
        }
    }

    fun updateSelectedDate(selectedDate: LocalDate) {
        _selectedDate.postValue(selectedDate)
        updateMenuList(selectedDate.toString().replace("-", "."))
    }

    private fun updateMenuList(selectedDate: String) {
        val cafeterias = _cafeteriaList.value ?: emptyList()

        cafeterias.forEach { it.date = it.date.substring(0 until 10) }

        val stuMenu = cafeterias.find {
            it.date == selectedDate && it.restaurant == resourceProvider.getString(R.string.cafeteria_student) && it.menuContent != "-"
        }?.menuContent ?: ""

        val eduMenu = cafeterias.find {
            it.date == selectedDate && it.restaurant == resourceProvider.getString(R.string.cafeteria_employee) && it.menuContent != "-"
        }?.menuContent ?: ""

        val stuMenus = if (stuMenu.isEmpty()) emptyList() else eduMenu.split(" ")
        val eduMenus = if (eduMenu.isEmpty()) emptyList() else eduMenu.split(" ")

        _stuMenuList.postValue(stuMenus.ifEmpty { listOf(resourceProvider.getString(R.string.cafeteria_no_menu)) })
        _eduMenuList.postValue(eduMenus.ifEmpty { listOf(resourceProvider.getString(R.string.cafeteria_no_menu)) })
    }


}

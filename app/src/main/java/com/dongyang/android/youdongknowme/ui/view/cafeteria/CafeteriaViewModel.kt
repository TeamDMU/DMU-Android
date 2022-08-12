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

    private val _cafeteriaList: MutableLiveData<List<Cafeteria>> = MutableLiveData()

    private val _stuMenuList: MutableLiveData<List<String>> = MutableLiveData()
    val stuMenuList: LiveData<List<String>> = _stuMenuList

    private val _eduMenuList: MutableLiveData<List<String>> = MutableLiveData()
    val eduMenuList: LiveData<List<String>> = _eduMenuList

    init {
        fetchCafeteria()
        updateSelectedDate(LocalDate.now())
    }

    fun updateSelectedDate(selectedDate: LocalDate) {
        _selectedDate.postValue(selectedDate)
        updateMenuList(selectedDate.toString().replace("-", "."))
    }

    private fun updateMenuList(selectedDate: String) {
        val cafeterias = _cafeteriaList.value ?: emptyList()

        // TODO : 파싱 관련 로직은 모두 dataSource 클래스로 옮기기
        cafeterias.forEach{ it.date = it.date.substring(0 until 10) }

        val stuMenu = cafeterias.find {
            it.date == selectedDate && it.restaurant == "학생식당" && it.menuContent != "-"
        }?.menuContent ?: ""

        val eduMenu = cafeterias.find {
            it.date == selectedDate && it.restaurant == "교직원식당" && it.menuContent != "-"
        }?.menuContent ?: ""

        val stuMenus = if(stuMenu.isEmpty()) emptyList() else eduMenu.split(" ")
        val eduMenus = if(eduMenu.isEmpty()) emptyList() else eduMenu.split(" ")

        _stuMenuList.postValue(stuMenus.ifEmpty { listOf("메뉴가 존재하지 않습니다") })
        _eduMenuList.postValue(eduMenus.ifEmpty { listOf("메뉴가 존재하지 않습니다") })
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
}

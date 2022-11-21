package com.dongyang.android.youdongknowme.ui.view.cafeteria

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Cafeteria
import com.dongyang.android.youdongknowme.data.repository.CafeteriaRepository
import com.dongyang.android.youdongknowme.standard.base.BaseViewModel
import com.dongyang.android.youdongknowme.standard.network.NetworkResult
import com.dongyang.android.youdongknowme.ui.view.util.Event
import com.dongyang.android.youdongknowme.ui.view.util.ResourceProvider
import kotlinx.coroutines.launch
import java.time.LocalDate

class CafeteriaViewModel(
    private val cafeteriaRepository: CafeteriaRepository,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel() {

    private val _errorState: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorState: LiveData<Event<Int>> = _errorState

    private val _isError: MutableLiveData<Boolean> = MutableLiveData()
    val isError: LiveData<Boolean> = _isError

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _selectedDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _cafeteriaList: MutableLiveData<List<Cafeteria>> = MutableLiveData()
    val cafeteriaList: LiveData<List<Cafeteria>> = _cafeteriaList

    private val _stuMenus: MutableLiveData<Pair<List<String>, List<String>>> = MutableLiveData()
    val stuMenus: LiveData<Pair<List<String>, List<String>>> = _stuMenus

    private val _eduMenus: MutableLiveData<Pair<List<String>, List<String>>> = MutableLiveData()
    val eduMenus: LiveData<Pair<List<String>, List<String>>> = _eduMenus

    init {
        fetchCafeteria()
    }

    fun fetchCafeteria() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            when (val result = cafeteriaRepository.fetchMenuList()) {
                is NetworkResult.Success -> {
                    val menuList = result.data
                    _cafeteriaList.value = menuList
                    updateSelectedDate(LocalDate.now())
                    _isError.postValue(false)
                    _isLoading.postValue(false)
                }
                is NetworkResult.Error -> {
                    handleError(result, _errorState)
                    _isError.postValue(true)
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun updateSelectedDate(selectedDate: LocalDate) {
        _selectedDate.value = selectedDate
        updateMenuList(selectedDate.toString().replace("-", "."))
    }

    private fun updateMenuList(selectedDate: String) {
        val cafeteriaList = _cafeteriaList.value ?: emptyList()

        cafeteriaList.forEach { it.date = it.date.substring(0 until 10) }

        val stuMenu = cafeteriaList.find {
            it.date == selectedDate && it.restaurant == resourceProvider.getString(R.string.cafeteria_student) && it.menuContent != "-"
        }?.menuContent ?: ""

        val eduMenu = cafeteriaList.find {
            it.date == selectedDate && it.restaurant == resourceProvider.getString(R.string.cafeteria_employee) && it.menuContent != "-"
        }?.menuContent ?: ""

        _stuMenus.value = parsingMenu(stuMenu)
        _eduMenus.value = parsingMenu(eduMenu)
    }

    private fun parsingMenu(menu: String): Pair<List<String>, List<String>> {
        val menus = if (menu.isEmpty()) {
            emptyList()
        } else {
            menu.replace("\n", "").replace("한식-", "").replace("일품-", "\n")
                .split("\n")
        }

        val koreanMenus = if (menus.isEmpty()) {
            listOf(resourceProvider.getString(R.string.cafeteria_no_menu))
        } else {
            menus[0].split(" ").filter { it != "" }
        }

        val anotherMenus = if (menus.size == 2) {
            if (menus.isEmpty()) {
                listOf(resourceProvider.getString(R.string.cafeteria_no_menu))
            } else {
                menus[1].split(" ")
                    .filter { it != "" }
            }
        } else {
            listOf(resourceProvider.getString(R.string.cafeteria_no_menu))
        }

        return Pair(koreanMenus, anotherMenus)
    }
}

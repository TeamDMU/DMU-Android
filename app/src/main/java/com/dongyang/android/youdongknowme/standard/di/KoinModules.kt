package com.dongyang.android.youdongknowme.standard.di

import com.dongyang.android.youdongknowme.data.repository.NoticeRepository
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeViewModel
import com.dongyang.android.youdongknowme.ui.view.schedule.ScheduleViewModel
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NoticeViewModel(get())
    }
    viewModel {
        ScheduleViewModel()
    }
    viewModel {
        SettingViewModel()
    }
}

val repositoryModule = module {
    single {
        NoticeRepository()
    }
}
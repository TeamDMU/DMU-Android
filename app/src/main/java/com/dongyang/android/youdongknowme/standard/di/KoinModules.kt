package com.dongyang.android.youdongknowme.standard.di

import com.dongyang.android.youdongknowme.data.repository.*
import com.dongyang.android.youdongknowme.ui.view.depart.DepartViewModel
import com.dongyang.android.youdongknowme.ui.view.detail.DetailViewModel
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeViewModel
import com.dongyang.android.youdongknowme.ui.view.schedule.ScheduleViewModel
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import com.dongyang.android.youdongknowme.ui.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        NoticeViewModel(get())
    }
    viewModel {
        ScheduleViewModel(get())
    }
    viewModel {
        SettingViewModel()
    }
    viewModel {
        DetailViewModel(get())
    }
    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        DepartViewModel(get())
    }
}

val repositoryModule = module {
    single {
        NoticeRepository()
    }
    single {
        DetailRepository()
    }
    single {
        SplashRepository()
    }
    single {
        DepartRepository()
    }
    single {
        ScheduleRepository()
    }
}
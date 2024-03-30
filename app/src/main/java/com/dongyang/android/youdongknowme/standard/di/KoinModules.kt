package com.dongyang.android.youdongknowme.standard.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dongyang.android.youdongknowme.data.local.UserDatabase
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import com.dongyang.android.youdongknowme.data.repository.AlarmRepository
import com.dongyang.android.youdongknowme.data.repository.CafeteriaRepository
import com.dongyang.android.youdongknowme.data.repository.DepartRepository
import com.dongyang.android.youdongknowme.data.repository.KeywordRepository
import com.dongyang.android.youdongknowme.data.repository.MainRepository
import com.dongyang.android.youdongknowme.data.repository.NoticeRepository
import com.dongyang.android.youdongknowme.data.repository.ScheduleRepository
import com.dongyang.android.youdongknowme.data.repository.SettingRepository
import com.dongyang.android.youdongknowme.data.repository.SplashRepository
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.ui.view.alarm.AlarmViewModel
import com.dongyang.android.youdongknowme.ui.view.cafeteria.CafeteriaViewModel
import com.dongyang.android.youdongknowme.ui.view.depart.DepartViewModel
import com.dongyang.android.youdongknowme.ui.view.detail.DetailViewModel
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordViewModel
import com.dongyang.android.youdongknowme.ui.view.license.LicenseViewModel
import com.dongyang.android.youdongknowme.ui.view.main.MainViewModel
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeViewModel
import com.dongyang.android.youdongknowme.ui.view.schedule.ScheduleViewModel
import com.dongyang.android.youdongknowme.ui.view.search.SearchViewModel
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import com.dongyang.android.youdongknowme.ui.view.splash.SplashViewModel
import com.dongyang.android.youdongknowme.ui.view.util.ResourceProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            UserDatabase::class.java, "YouDongKnowMe_DB"
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(IO).launch {
                        get<UserDatabase>().keywordDao().insertKeywordList(defaultKeywordList)
                    }
                }
            })
            .build()
    }
    single {
        get<UserDatabase>().keywordDao()
    }
    single {
        get<UserDatabase>().alarmDao()
    }
}

val networkModule = module {
    single {
        ErrorResponseHandler()
    }
}

val viewModelModule = module {
    viewModel {
        NoticeViewModel(get())
    }
    viewModel {
        ScheduleViewModel(get())
    }
    viewModel {
        SettingViewModel(get())
    }
    viewModel {
        DetailViewModel()
    }
    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        DepartViewModel(get())
    }
    viewModel {
        KeywordViewModel(get())
    }
    viewModel {
        AlarmViewModel(get())
    }
    viewModel {
        CafeteriaViewModel(get(), get())
    }
    viewModel {
        LicenseViewModel()
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel{
        MainViewModel(get())
    }
}

val repositoryModule = module {
    single {
        NoticeRepository(get())
    }
    single {
        SplashRepository()
    }
    single {
        DepartRepository()
    }
    single {
        ScheduleRepository(get())
    }
    single {
        KeywordRepository(get())
    }
    single {
        SettingRepository(get(), get())
    }
    single {
        AlarmRepository(get())
    }
    single {
        CafeteriaRepository(get())
    }
    single{
        MainRepository(get(), get())
    }
}

val utilModule = module {
    single {
        ResourceProvider(androidContext())
    }
}

private val defaultKeywordList = arrayListOf(
    KeywordEntity("시험", "exam", false),
    KeywordEntity("수강", "signup", false),
    KeywordEntity("특강", "speciallecture", false),
    KeywordEntity("계절학기", "seasonalsemester", false),
    KeywordEntity("휴학", "leaveofabsence", false),
    KeywordEntity("복학", "returntoschool", false),
    KeywordEntity("졸업", "graduate", false),
    KeywordEntity("전과", "switchmajors", false),
    KeywordEntity("학기포기", "givingupthesemester", false),
    KeywordEntity("장학", "scholarship", false),
    KeywordEntity("국가장학", "nationalscholarship", false),
    KeywordEntity("등록", "registration", false),
    KeywordEntity("채용", "employment", false),
    KeywordEntity("공모전", "contest", false),
    KeywordEntity("대회", "competition", false),
    KeywordEntity("현장실습", "fieldtraining", false),
    KeywordEntity("봉사", "volunteer", false),
    KeywordEntity("기숙사", "dormitory", false),
    KeywordEntity("동아리", "group", false),
    KeywordEntity("학생회", "studentcouncil", false),
)

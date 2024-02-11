package com.dongyang.android.youdongknowme.standard.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dongyang.android.youdongknowme.data.local.UserDatabase
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import com.dongyang.android.youdongknowme.data.repository.AlarmRepository
import com.dongyang.android.youdongknowme.data.repository.CafeteriaRepository
import com.dongyang.android.youdongknowme.data.repository.DepartRepository
import com.dongyang.android.youdongknowme.data.repository.DetailRepository
import com.dongyang.android.youdongknowme.data.repository.KeywordRepository
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
        DetailViewModel(get())
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
        SearchViewModel()
    }
}

val repositoryModule = module {
    single {
        NoticeRepository(get(), get())
    }
    single {
        DetailRepository(get())
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
        SettingRepository()
    }
    single {
        AlarmRepository(get())
    }
    single {
        CafeteriaRepository(get())
    }
}

val utilModule = module {
    single {
        ResourceProvider(androidContext())
    }
}

private val defaultKeywordList = arrayListOf(
    KeywordEntity("시험", "exam", false),
    KeywordEntity("수강", "course", false),
    KeywordEntity("특강", "lecture", false),
    KeywordEntity("계절학기", "season", false),
    KeywordEntity("휴학", "leave", false),
    KeywordEntity("복학", "return", false),
    KeywordEntity("졸업", "graduation", false),
    KeywordEntity("전과", "transfer", false),
    KeywordEntity("학기포기", "drop", false),
    KeywordEntity("장학", "scholarship", false),
    KeywordEntity("국가장학", "nationalScholarship", false),
    KeywordEntity("등록", "tuition", false),
    KeywordEntity("채용", "recruitment", false),
    KeywordEntity("공모전", "contest", false),
    KeywordEntity("대회", "competition", false),
    KeywordEntity("현장실습", "field", false),
    KeywordEntity("봉사", "service", false),
    KeywordEntity("기숙사", "dormitory", false),
    KeywordEntity("동아리", "club", false),
    KeywordEntity("학생회", "studentCouncil", false),
)

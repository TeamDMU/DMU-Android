package com.dongyang.android.youdongknowme.standard.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dongyang.android.youdongknowme.data.local.UserDatabase
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import com.dongyang.android.youdongknowme.data.repository.*
import com.dongyang.android.youdongknowme.standard.network.ErrorResponseHandler
import com.dongyang.android.youdongknowme.standard.network.RetrofitObject
import com.dongyang.android.youdongknowme.ui.view.alarm.AlarmViewModel
import com.dongyang.android.youdongknowme.ui.view.depart.DepartViewModel
import com.dongyang.android.youdongknowme.ui.view.detail.DetailViewModel
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordViewModel
import com.dongyang.android.youdongknowme.ui.view.main.MainViewModel
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeViewModel
import com.dongyang.android.youdongknowme.ui.view.schedule.ScheduleViewModel
import com.dongyang.android.youdongknowme.ui.view.setting.SettingViewModel
import com.dongyang.android.youdongknowme.ui.view.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module{
    single{
        Room.databaseBuilder(get(),
            UserDatabase::class.java,"YouDongKnowMe_DB")
            .fallbackToDestructiveMigration()
            .addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(IO).launch{
                        get<UserDatabase>().keywordDao().insertKeywordList(defaultKeywordList)
                    }
                }
            })
            .build()
    }
    single{
        get<UserDatabase>().keywordDao()
    }
    single{
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
        MainViewModel()
    }
    viewModel {
        AlarmViewModel(get())
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
}

private val defaultKeywordList = arrayListOf<KeywordEntity>(
    KeywordEntity("시험", "exam", false),
    KeywordEntity("수강", "course", false),
    KeywordEntity("특강", "lecture", false),
    KeywordEntity("계절학기", "season", false),
    KeywordEntity("장학", "scholarship", false),
    KeywordEntity("국가장학", "kosaf", false),
    KeywordEntity("등록", "tuition", false),
    KeywordEntity("휴학", "leave", false),
    KeywordEntity("복학", "return", false),
    KeywordEntity("졸업", "graduation", false),
    KeywordEntity("전과", "transfer", false),
    KeywordEntity("학기포기", "drop", false),
    KeywordEntity("채용", "recruitment", false),
    KeywordEntity("공모전", "contest", false),
    KeywordEntity("현장실습", "field", false),
    KeywordEntity("대회", "competition", false),
    KeywordEntity("봉사", "service", false),
    KeywordEntity("기숙사", "dormitory", false),
    KeywordEntity("코로나19", "corona", false),
    KeywordEntity("동아리", "club", false),
)

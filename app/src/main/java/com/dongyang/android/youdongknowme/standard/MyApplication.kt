package com.dongyang.android.youdongknowme.standard

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.dongyang.android.youdongknowme.data.local.SharedPreference
import com.dongyang.android.youdongknowme.standard.di.databaseModule
import com.dongyang.android.youdongknowme.standard.di.networkModule
import com.dongyang.android.youdongknowme.standard.di.repositoryModule
import com.dongyang.android.youdongknowme.standard.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

/**
 * 앱 실행 시 가장 먼저 진입
 */
class MyApplication : Application(), LifecycleEventObserver {
    companion object {
        var isForeground = false

        lateinit var instance: MyApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, "Debug[$tag]", message, t)
            }
        })

        // SharedPreference 초기화
        SharedPreference.getInstance(this)

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        startKoin {
            androidContext(this@MyApplication)
            androidFileProperties()
            modules(listOf(databaseModule, repositoryModule, viewModelModule, networkModule))
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            isForeground = true
        } else if (event == Lifecycle.Event.ON_STOP) {
            isForeground = false
        }
    }
}
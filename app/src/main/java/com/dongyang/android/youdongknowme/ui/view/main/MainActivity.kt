package com.dongyang.android.youdongknowme.ui.view.main

import android.content.Context
import android.content.Intent
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityMainBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.util.KeepStateNavigator
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/* 메인 액티비티 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun initStartView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_container) as NavHostFragment
        val navController = navHostFragment.navController

        val navigator =
            KeepStateNavigator(
                this,
                navHostFragment.childFragmentManager,
                binding.mainNavContainer.id
            )
        navController.navigatorProvider += navigator
        navController.setGraph(R.navigation.dmu_navigation)
        binding.mainNvBottom.setupWithNavController(navController)
    }

    override fun initDataBinding() {
        viewModel.isFirstLaunch.observe(this) { boolean ->
            if (boolean) getFcmToken()
        }
    }

    override fun initAfterBinding() = Unit

    private fun getFcmToken() {
        viewModel.setIsFirstLaunch(false)
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModel.setFCMToken(task.result).run { viewModel.setInitToken() }
                Timber.d("token ${task.result}")
            } else {
                return@addOnCompleteListener
            }
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
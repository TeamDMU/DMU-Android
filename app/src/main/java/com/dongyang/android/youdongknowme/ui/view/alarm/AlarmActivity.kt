package com.dongyang.android.youdongknowme.ui.view.alarm

import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityAlarmBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmActivity : BaseActivity<ActivityAlarmBinding, AlarmViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_alarm
    override val viewModel: AlarmViewModel by viewModel()

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}
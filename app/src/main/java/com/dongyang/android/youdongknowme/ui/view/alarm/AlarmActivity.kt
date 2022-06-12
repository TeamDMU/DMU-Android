package com.dongyang.android.youdongknowme.ui.view.alarm

import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityAlarmBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.adapter.AlarmAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmActivity : BaseActivity<ActivityAlarmBinding, AlarmViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_alarm
    override val viewModel: AlarmViewModel by viewModel()

    private val adapter = AlarmAdapter()

    override fun initStartView() {
        binding.alarmRcv.apply {
            this.adapter = this@AlarmActivity.adapter
            this.layoutManager = LinearLayoutManager(this@AlarmActivity)
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.alarmList.observe(this) {
            adapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
        viewModel.getAlarms()
    }
}
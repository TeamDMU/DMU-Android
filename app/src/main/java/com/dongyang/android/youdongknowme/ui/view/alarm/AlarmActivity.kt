package com.dongyang.android.youdongknowme.ui.view.alarm

import android.content.Intent
import android.view.View
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import com.dongyang.android.youdongknowme.databinding.ActivityAlarmBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.standard.util.mapDepartmentKoreanToCode
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmActivity : BaseActivity<ActivityAlarmBinding, AlarmViewModel>(), AlarmClickListener {
    override val layoutResourceId: Int = R.layout.activity_alarm
    override val viewModel: AlarmViewModel by viewModel()

    override fun initStartView() {
        binding.vm = viewModel
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.alarmToolbar.apply {
            tvToolbarTitle.text = getString(R.string.alarm_title)
            tvToolbarExitButton.visibility = View.VISIBLE
            tvToolbarActionButton.text = getString(R.string.alarm_action_button_text)
            tvToolbarActionButton.visibility = View.VISIBLE
        }
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        viewModel.getAlarms()

        binding.alarmToolbar.tvToolbarExitButton.setOnClickListener {
            finish()
        }
    }

    // 아이템 클릭시 자세히 보기 화면으로 이동
    override fun itemClick(alarmEntity: AlarmEntity) {
        val departCode = mapDepartmentKoreanToCode(alarmEntity.department)

        alarmEntity.id?.let { viewModel.updateIsVisitedAlarm(true, it) }

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("departCode", departCode)
        intent.putExtra("boardNum", alarmEntity.num)
        startActivity(intent)
    }
}
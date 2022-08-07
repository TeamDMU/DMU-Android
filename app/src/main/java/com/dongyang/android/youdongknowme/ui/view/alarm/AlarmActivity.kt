package com.dongyang.android.youdongknowme.ui.view.alarm

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.local.entity.AlarmEntity
import com.dongyang.android.youdongknowme.databinding.ActivityAlarmBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.standard.util.logd
import com.dongyang.android.youdongknowme.standard.util.mapDepartmentKoreanToCode
import com.dongyang.android.youdongknowme.ui.adapter.AlarmAdapter
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlarmActivity : BaseActivity<ActivityAlarmBinding, AlarmViewModel>(), AlarmClickListener {
    override val layoutResourceId: Int = R.layout.activity_alarm
    override val viewModel: AlarmViewModel by viewModel()

    private lateinit var adapter : AlarmAdapter

    override fun initStartView() {
        adapter = AlarmAdapter().apply { setItemClickListener(this@AlarmActivity) }
        binding.vm = viewModel
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

        binding.alarmExitBtn.setOnClickListener {
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
package com.dongyang.android.youdongknowme.ui.view.depart

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDepartBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.adapter.DepartAdapter
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DepartActivity : BaseActivity<ActivityDepartBinding, DepartViewModel>(), DepartClickListener {


    override val layoutResourceId: Int = R.layout.activity_depart
    override val viewModel: DepartViewModel by viewModel()
    private lateinit var adapter: DepartAdapter
    private lateinit var items: ArrayList<String>

    override fun initStartView() {
        items =
            resources.getStringArray(R.array.dmu_department_list).toCollection(ArrayList<String>())
        items.sort()

        // 학과 리스트
        adapter = DepartAdapter().apply {
            submitList(items)
            setItemClickListener(this@DepartActivity)
        }

        binding.rvDepart.apply {
            this.adapter = this@DepartActivity.adapter
            this.layoutManager = LinearLayoutManager(this@DepartActivity)
            this.setHasFixedSize(true)
        }

    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        viewModel.selectDepartPosition.observe(this) {
            adapter.submitPosition(it)

            // 포지션 선택 시 스낵바를 통해 알림 표시
            if (it != -1) getDepart(items)
        }
        
        binding.toolbarDepart.btnToolbarExit.setOnClickListener { finish() }
    }

    // 컨테이너 클릭 시 선택한 학과의 포지션 저장
    override fun containerClick(position: Int) {
        viewModel.setSelectPosition(position)
    }

    // 확인 버튼을 누르면 내부 DB에 학과를 담고 메인 액티비티로 이동
    private fun getDepart(items: ArrayList<String>) {
        return binding.btnDepartComplete.setOnClickListener {
            viewModel.setDepartment(items[viewModel.selectDepartPosition.value ?: 0])
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}

package com.dongyang.android.youdongknowme.ui.view.depart

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityOnboardingDepartBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.adapter.OnboardingDepartAdapter
import com.dongyang.android.youdongknowme.ui.view.keyword.OnboardingKeywordActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingDepartActivity : BaseActivity<ActivityOnboardingDepartBinding, DepartViewModel>(),
    DepartClickListener {

    override val layoutResourceId: Int = R.layout.activity_onboarding_depart
    override val viewModel: DepartViewModel by viewModel()
    private lateinit var adapter: OnboardingDepartAdapter
    private lateinit var items: ArrayList<String>
    private var searchList = ArrayList<String>()
    private var emptyList = arrayListOf<String>("")

    override fun initStartView() {
        // 학과 리스트
        items =
            resources.getStringArray(R.array.dmu_department_list).toCollection(ArrayList<String>())
        items.sort()

        adapter = OnboardingDepartAdapter().apply {
            submitList(emptyList)
            setItemClickListener(this@OnboardingDepartActivity)
        }

        binding.rvOnboardingDepart.apply {
            this.adapter = this@OnboardingDepartActivity.adapter
            this.layoutManager = LinearLayoutManager(this@OnboardingDepartActivity)
            this.setHasFixedSize(true)
        }

        // 부분 색상 지정
        setSpanText(binding.tvOnboardingDepartTitleMain, startIdx = 0, endIdx = 5)
    }

    override fun initDataBinding() = Unit

    override fun initAfterBinding() {
        binding.ibOnboardingDepartSearchClear.setOnClickListener {
            binding.etOnboardingDepartSearch.text.clear()
            setSearchColor(false)
        }

        binding.etOnboardingDepartSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) =
                Unit

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) = Unit

            override fun afterTextChanged(editable: Editable?) {
                val searchText =
                    binding.etOnboardingDepartSearch.text.toString().replace("\\s".toRegex(), "")
                searchList = ArrayList<String>()

                if (searchText.isEmpty()) {
                    adapter.submitList(emptyList)
                } else {
                    // 검색 단어를 포함하는지 확인
                    for (item in items) {
                        if (item.contains(searchText)) {
                            searchList.add(item)

                            if (item == searchText) {
                                setSearchColor(true)
                            } else {
                                setSearchColor(false)
                            }
                        }
                    }
                    adapter.submitList(searchList)
                }
            }
        })

        viewModel.selectDepartPosition.observe(this) {
            adapter.submitPosition(it)

            if (it != -1) {
                binding.etOnboardingDepartSearch.setText(
                    searchList[viewModel.selectDepartPosition.value ?: 0]
                )
                binding.etOnboardingDepartSearch.setSelection(binding.etOnboardingDepartSearch.text.length)
            }
        }

        binding.btnOnboardingDepartNext.setOnClickListener {
            getDepart(searchList)
        }
    }

    private fun setSearchColor(isContains: Boolean) {
        if (isContains) {
            binding.linearLayoutOnboardingDepartSearch.setBackgroundResource(R.drawable.bg_stroke_blue300_radius_2dp)

            binding.etOnboardingDepartSearch.compoundDrawableTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@OnboardingDepartActivity,
                        R.color.blue300
                    )
                )
        } else {
            viewModel.setSelectPosition(-1)

            binding.linearLayoutOnboardingDepartSearch.setBackgroundResource(R.drawable.bg_stroke_gray300_radius_2dp)
            binding.etOnboardingDepartSearch.compoundDrawableTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this@OnboardingDepartActivity,
                        R.color.gray300
                    )
                )
        }
    }

    // 컨테이너 클릭 시 선택한 학과의 포지션 저장
    override fun containerClick(position: Int) {
        if (searchList.isEmpty()) {
            return
        }
        viewModel.setSelectPosition(position)
    }

    // 확인 버튼을 누르면 내부 DB에 학과를 담고 메인 액티비티로 이동
    private fun getDepart(items: ArrayList<String>) {
        if (viewModel.selectDepartPosition.value != -1) {
            viewModel.setDepartment(items[viewModel.selectDepartPosition.value ?: 0])
            val intent = Intent(this, OnboardingKeywordActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, R.string.toast_msg_department, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, OnboardingDepartActivity::class.java)
        }
    }
}
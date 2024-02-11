package com.dongyang.android.youdongknowme.ui.view.keyword

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.lifecycle.Observer
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.local.entity.KeywordEntity
import com.dongyang.android.youdongknowme.databinding.ActivityKeywordBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.FirebaseApp
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class KeywordActivity : BaseActivity<ActivityKeywordBinding, KeywordViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_keyword
    override val viewModel: KeywordViewModel by viewModel()

    override fun initStartView() {
        binding.vm = viewModel

        // 부분 색상 지정
        setSpan(binding.tvKeywordTitleMain,startIdx = 0, endIdx = 3)
    }

    // 텍스트 부분 색상 설정
    private fun setSpan(spanTextView: TextView, startIdx: Int, endIdx: Int){
        SpannableStringBuilder(spanTextView.text).apply {
            setSpan(
                ForegroundColorSpan(getColor(R.color.main)),
                startIdx,
                endIdx,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanTextView.text = this
        }
    }

    override fun initDataBinding() {
        // 효율을 위해 단 한번만 옵저빙하여 이미 구독중인 항목을 선택 처리
        viewModel.localKeywordList.observe(this, object : Observer<List<KeywordEntity>> {
            override fun onChanged(t: List<KeywordEntity>?) {
                viewModel.setAllKeywords(t?.filter { it.isSubscribe }?.map { it.name }
                    ?: listOf(""))
                setCheckChipChange(
                    binding.chipGroupKeywordClass,
                    binding.chipGroupKeywordMoney,
                    binding.chipGroupKeywordAcademic,
                    binding.chipGroupKeywordEmployment,
                    binding.chipGroupKeywordEtc
                )
                viewModel.localKeywordList.removeObserver(this)
            }
        })
    }

    override fun initAfterBinding() {
        viewModel.checkFirstLaunch()
        viewModel.getLocalKeywordList()

        // TODO :: 안드로이드 데이터베이스에 유저별 설정한 키워드 저장 및 파이어베이스 키워드 구독 설정
        binding.btnKeywordNext.setOnClickListener {
            viewModel.subscribeCheckedKeyword()
            if (viewModel.isFirstLaunch.value == true) {
                viewModel.setFirstLaunch(false)
                val intent = Intent(this@KeywordActivity, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }

    private fun setCheckChipChange(vararg chipGroups: ChipGroup) {

        for (chipGroup in chipGroups) {
            for (index in 0 until chipGroup.childCount) {
                val chip: Chip = chipGroup.getChildAt(index) as Chip

                // 유저가 선택 및 설정한 키워드인 경우 체크한 것으로 설정
                if (chip.text in viewModel.checkKeywordList) {
                    chip.isChecked = true
                }

                chip.setOnCheckedChangeListener { view, isChecked ->
                    if (isChecked) {
                        viewModel.setCheckedKeywords(view.text.toString())
                    } else {
                        viewModel.removeCheckedKeywords(view.text.toString())
                    }
                }
            }
        }
    }
}
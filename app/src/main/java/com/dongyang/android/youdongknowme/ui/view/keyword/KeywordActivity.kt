package com.dongyang.android.youdongknowme.ui.view.keyword

import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityKeywordBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class KeywordActivity : BaseActivity<ActivityKeywordBinding, KeywordViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_keyword
    override val viewModel: KeywordViewModel by viewModel()

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {


        // 뒤로가기 버튼 눌렀을 때
        binding.keywordExitBtn.setOnClickListener {
            finish()
        }
    }


}
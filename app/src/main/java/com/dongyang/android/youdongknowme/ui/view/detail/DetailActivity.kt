package com.dongyang.android.youdongknowme.ui.view.detail

import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDepartBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

/* 공지사항 글 상세 화면 */
class DetailActivity : BaseActivity<ActivityDepartBinding, DetailViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_detail
    override val viewModel: DetailViewModel by viewModel()

    override fun initStartView() = Unit

    override fun initDataBinding() {
        viewModel.isLoading.observe(this) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.errorState.observe(this, EventObserver { resId ->
            showToast(getString(resId))
        })
    }

    override fun initAfterBinding() = Unit
}
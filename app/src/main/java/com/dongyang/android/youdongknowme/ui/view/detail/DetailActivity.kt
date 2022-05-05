package com.dongyang.android.youdongknowme.ui.view.detail

import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDetailBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.standard.util.log

/* 공지사항 글 상세 화면 */
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_detail
    override val viewModel: DetailViewModel by viewModel()

    private val num : Int by lazy {
        intent.getIntExtra("num", 0)
    }

    override fun initStartView() {

    }

    override fun initDataBinding() {
        viewModel.noticeDetail.observe(this) {
            binding.detail = it
        }
        viewModel.errorState.observe(this) { resId ->
            showToast(getString(resId))
        }
    }

    override fun initAfterBinding() {
        viewModel.getNoticeList(num)
        log(num.toString())
    }
}
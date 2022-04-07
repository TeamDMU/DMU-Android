package com.dongyang.android.youdongknowme.ui.view.notice

import androidx.lifecycle.ViewModelProvider
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment

/* 공지 사항 화면 */
class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticeViewModel>() {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by lazy {
        ViewModelProvider(this)[NoticeViewModel::class.java]
    }

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

}
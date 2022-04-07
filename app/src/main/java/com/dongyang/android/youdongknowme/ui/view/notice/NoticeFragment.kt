package com.dongyang.android.youdongknowme.ui.view.notice

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter

/* 공지 사항 화면 */
class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticeViewModel>() {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by lazy {
        ViewModelProvider(this)[NoticeViewModel::class.java]
    }

    private lateinit var adapter : NoticeAdapter

    override fun initStartView() {
        adapter = NoticeAdapter()
        binding.noticeRvList.apply {
            this.adapter = this@NoticeFragment.adapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(requireActivity(),1))
        }
    }

    override fun initDataBinding() {

        // TODO :: 테스트 코드!!! 후에 변경 요망
        val testCode = listOf(Notice(0,"[학부] 2022학년도 1학기 학습공동체(전공튜터링) 프로그램 시행 안내", "2022-04-07", "김정호"))
        adapter.submitList(testCode)
    }

    override fun initAfterBinding() {

    }

}
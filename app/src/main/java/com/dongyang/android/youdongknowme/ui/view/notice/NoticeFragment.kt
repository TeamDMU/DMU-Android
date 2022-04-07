package com.dongyang.android.youdongknowme.ui.view.notice

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.log
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter
import com.google.android.material.tabs.TabLayout
import java.lang.String
import kotlin.Int
import kotlin.apply
import kotlin.getValue
import kotlin.lazy

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

        // TODO :: Object Retrofit 생성하기
        // TODO :: 테스트 코드!!! 후에 변경 요망
        // TODO :: 데이터 업데이트하기!
        binding.noticeSwipe.setOnRefreshListener {
            binding.noticeSwipe.isRefreshing = false
        }

        binding.noticeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!!.text == "학부") {
                    val testCode = listOf(Notice(0,"[학부] 2022학년도 1학기 학습공동체(전공튜터링) 프로그램 시행 안내", "2022-04-07", "이계홍"))
                    adapter.submitList(testCode)
                } else {
                    val testCode = listOf(Notice(0,"[공지] 3월 31일(목)부터 적용될 수업 방침에 대한 안내 말씀 드립니다.", "2022-04-07", "김지원"))
                    adapter.submitList(testCode)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 구현 X
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 구현 X
            }

        })

        val testCode = listOf(Notice(0,"[학부] 2022학년도 1학기 학습공동체(전공튜터링) 프로그램 시행 안내", "2022-04-07", "김정호"))
        adapter.submitList(testCode)
    }

    override fun initAfterBinding() {

    }

}
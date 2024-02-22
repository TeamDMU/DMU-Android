package com.dongyang.android.youdongknowme.ui.view.notice

import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

/* 공지 사항 화면 */
class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticeViewModel>(), NoticeClickListener {

    override val layoutResourceId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by viewModel()

    private lateinit var adapter: NoticeAdapter

    override fun initStartView() {
        adapter = NoticeAdapter().apply { setItemClickListener(this@NoticeFragment) }
        binding.noticeRvList.apply {
            this.adapter = this@NoticeFragment.adapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.itemAnimator = null
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(requireContext(), 1))
        }
        setupTabLayout()
    }

    override fun initDataBinding() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.universityNotices.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                adapter.submitList(response)
            }
        }

        viewModel.departmentNotices.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                adapter.submitList(response)
            }
        }

        viewModel.errorState.observe(viewLifecycleOwner, EventObserver { resId ->
            showToast(getString(resId))
        })
    }

    override fun initAfterBinding() {

        binding.noticeSwipe.setOnRefreshListener {
//            viewModel.refreshNotices()
            binding.noticeSwipe.isRefreshing = false
        }

        binding.noticeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                binding.noticeRvList.scrollToPosition(0)

                if (tab.text == getString(R.string.notice_tab_university))
                    viewModel.fetchUniversityNotices()
                else
                    viewModel.fetchDepartmentNotices()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.noticeRvList.smoothScrollToPosition(-10)
            }
        })

        binding.noticeErrorContainer.refresh.setOnClickListener {
//            viewModel.refreshNotices()
        }
    }

    private fun setupTabLayout() {
        if (viewModel.selectedTab.value?.peekContent() == NoticeTabType.FACULTY)
            binding.noticeTab.getTabAt(1)?.select()
    }

    override fun itemClick(num: Int) {
        val departCode = viewModel.departmentCode.value

        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("departCode", departCode)
        intent.putExtra("boardNum", num)
        startActivity(intent)
    }
}
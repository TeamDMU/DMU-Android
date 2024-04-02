package com.dongyang.android.youdongknowme.ui.view.notice

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticeViewModel>() {

    override val layoutResourceId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by viewModel()

    private lateinit var adapter: NoticeAdapter

    override fun initStartView() {
        adapter = NoticeAdapter { url -> navigateToDetail(url) }
        binding.noticeRvList.apply {
            this.adapter = this@NoticeFragment.adapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.itemAnimator = null
            this.setHasFixedSize(true)
        }
        setupTabLayout()
        setupInfinityScroll()
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
            viewModel.refreshNotices()
            binding.noticeSwipe.isRefreshing = false
        }

        binding.noticeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                binding.noticeRvList.scrollToPosition(0)

                if (tab.text == getString(R.string.notice_tab_university)) {
                    viewModel.updateSelectedTabType(NoticeTabType.SCHOOL)
                    viewModel.fetchUniversityNotices()
                } else {
                    viewModel.updateSelectedTabType(NoticeTabType.FACULTY)
                    viewModel.fetchDepartmentNotices()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.noticeRvList.smoothScrollToPosition(-10)
            }
        })

        binding.noticeErrorContainer.refresh.setOnClickListener {
            viewModel.fetchUniversityNotices()
        }
    }

    private fun setupTabLayout() {
        if (viewModel.selectedTab.value?.peekContent() == NoticeTabType.FACULTY)
            binding.noticeTab.getTabAt(1)?.select()
    }

    private fun setupInfinityScroll() {
        binding.noticeRvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!viewModel.isLoading.value!! && lastVisibleItemPosition >= totalItemCount - 1) {
                    viewModel.selectedTab.value?.peekContent()?.let { currentTab ->
                        when (currentTab) {
                            NoticeTabType.FACULTY -> viewModel.fetchDepartmentNotices()
                            NoticeTabType.SCHOOL -> viewModel.fetchUniversityNotices()
                        }
                    }
                }
            }
        })
    }

    private fun navigateToDetail(url: String) {
        val intent = DetailActivity.newIntent(requireContext(), url)
        startActivity(intent)
    }
}
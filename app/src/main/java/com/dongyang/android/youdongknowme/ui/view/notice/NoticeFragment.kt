package com.dongyang.android.youdongknowme.ui.view.notice

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.inputmethod.EditorInfo
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.ACTION
import com.dongyang.android.youdongknowme.standard.util.hideKeyboard
import com.dongyang.android.youdongknowme.standard.util.showKeyboard
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter
import com.dongyang.android.youdongknowme.ui.view.alarm.AlarmActivity
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

/* 공지 사항 화면 */
class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticeViewModel>(), NoticeClickListener {

    override val layoutResourceId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by viewModel()

    private val badgeDrawable: BadgeDrawable by lazy {
        BadgeDrawable.create(requireActivity())
    }

    private lateinit var adapter: NoticeAdapter

    private val localBroadCast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.refreshNotices()
        }
    }

    override fun initStartView() {
        binding.vm = viewModel
        adapter = NoticeAdapter().apply { setItemClickListener(this@NoticeFragment) }
        binding.noticeRvList.apply {
            this.adapter = this@NoticeFragment.adapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.itemAnimator = null
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(requireActivity(), 1))
        }
        setupTabLayout()
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun initDataBinding() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.noticeList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.errorState.observe(viewLifecycleOwner, EventObserver { resId ->
            showToast(getString(resId))
        })
//
//        // 알람 카운트가 0이 아닌 경우에 뱃지 추가
//        viewModel.unVisitedAlarmCount.observe(viewLifecycleOwner) { count ->
//            if (count == 0) {
//                binding.noticeToolbar.toolbarAlarm.post {
//                    BadgeUtils.detachBadgeDrawable(
//                        badgeDrawable,
//                        binding.noticeToolbar.toolbarAlarm
//                    )
//                }
//            } else {
//                badgeDrawable.number = count
//                binding.noticeToolbar.toolbarAlarm.post {
//                    BadgeUtils.attachBadgeDrawable(
//                        badgeDrawable,
//                        binding.noticeToolbar.toolbarAlarm,
//                        binding.noticeToolbar.toolbarAlarmContainer
//                    )
//                }
//            }
//        }
    }

    override fun initAfterBinding() {
        viewModel.getUnVisitedAlarmCount()

        // 새로고침 했을 때 동작
        binding.noticeSwipe.setOnRefreshListener {
            viewModel.refreshNotices()
            binding.noticeSwipe.isRefreshing = false
        }

//        // 툴바의 검색 버튼 눌렀을 때 동작
//        binding.noticeToolbar.toolbarSearch.setOnClickListener {
//            // 최초 검색 버튼 클릭 시 EditText 보여지게 설정
//            if (viewModel.isSearchMode.value == false) {
//                binding.noticeToolbar.toolbarSearchText.requestFocus()
//                binding.noticeToolbar.toolbarSearchText.showKeyboard()
//                viewModel.updateSearchMode(true)
//                YoYo.with(Techniques.FadeInUp)
//                    .duration(400)
//                    .playOn(binding.noticeToolbar.toolbarSearchView)
//            } else {
//                binding.noticeToolbar.toolbarSearchText.hideKeyboard()
//                binding.noticeToolbar.toolbarSearchText.text.clear()
//                viewModel.updateSearchMode(false)
//                YoYo.with(Techniques.FadeOutDown)
//                    .duration(400)
//                    .playOn(binding.noticeToolbar.toolbarSearchView)
//            }
//        }

//        binding.noticeToolbar.toolbarSearchText.setOnEditorActionListener { textView, actionId, _ ->
//            val searchKeyword = textView.text.toString()
//            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
//                textView.hideKeyboard()
//                viewModel.fetchSearchNotices(searchKeyword)
//                binding.noticeRvList.scrollToPosition(0)
//            }
//
//            false
//        }

        // 각각 탭 버튼 눌렀을 때 동작
        binding.noticeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                binding.noticeRvList.scrollToPosition(0)

                if (tab.text == getString(R.string.notice_tab_university))
                    viewModel.updateSelectedTabType(NoticeTabType.SCHOOL)
                else
                    viewModel.updateSelectedTabType(NoticeTabType.FACULTY)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            // 다시 클릭시 스크롤되게 설정
            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.noticeRvList.smoothScrollToPosition(-10)
            }
        })

        binding.noticeErrorContainer.refresh.setOnClickListener {
            viewModel.refreshNotices()
        }
    }

    private fun setupTabLayout() {
        if (viewModel.selectedTab.value?.peekContent() == NoticeTabType.FACULTY)
            binding.noticeTab.getTabAt(1)?.select()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ACTION.FCM_ACTION_NAME)
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(localBroadCast, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(localBroadCast)
    }

    // 아이템 클릭시 자세히 보기 화면으로 이동
    override fun itemClick(num: Int) {
        val departCode = viewModel.departmentCode.value

        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("departCode", departCode)
        intent.putExtra("boardNum", num)
        startActivity(intent)
    }
}
package com.dongyang.android.youdongknowme.ui.view.notice

import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.hideKeyboard
import com.dongyang.android.youdongknowme.standard.util.showKeyboard
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordActivity
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

/* 공지 사항 화면 */
class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticeViewModel>(), NoticeClickListener {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by viewModel()

    private lateinit var adapter: NoticeAdapter

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
    }

    override fun initDataBinding() {

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.noticeList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.errorState.observe(viewLifecycleOwner) { resId ->
            showToast(getString(resId))
        }

        viewModel.isUniversityTab.observe(viewLifecycleOwner) {
            // 구성 변경에 대비, 선택한 탭이 무엇인지 저장
            viewModel.setDepartmentCode()
            if (!it) {
                binding.noticeTab.getTabAt(1)?.select()
            }
        }
    }

    override fun initAfterBinding() {
        // 새로고침 했을 때 동작
        binding.noticeSwipe.setOnRefreshListener {
            viewModel.refreshNotices()
            binding.noticeSwipe.isRefreshing = false
        }

        // 툴바의 검색 버튼 눌렀을 때 동작
        binding.noticeToolbar.toolbarSearch.setOnClickListener {
            // 최초 검색 버튼 클릭 시 EditText 보여지게 설정
            if (viewModel.isSearchMode.value == false) {
                binding.noticeToolbar.toolbarSearchText.requestFocus()
                binding.noticeToolbar.toolbarSearchText.showKeyboard()
                viewModel.setSearchMode(true)
                YoYo.with(Techniques.FadeInUp)
                    .duration(400)
                    .playOn(binding.noticeToolbar.toolbarSearchView)
            } else {
                binding.noticeToolbar.toolbarSearchText.hideKeyboard()
                binding.noticeToolbar.toolbarSearchText.text.clear()
                viewModel.setSearchMode(false)
                YoYo.with(Techniques.FadeOutDown)
                    .duration(400)
                    .playOn(binding.noticeToolbar.toolbarSearchView)
            }
        }

        binding.noticeToolbar.toolbarSearchText.setOnEditorActionListener { textView, actionId, _ ->
            val searchKeyword = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchKeyword.isNotEmpty()) {
                textView.hideKeyboard()
                viewModel.fetchSearchNotices(searchKeyword)
                binding.noticeRvList.scrollToPosition(0)
            }

            false
        }

        binding.noticeToolbar.toolbarAlarm.setOnClickListener {
            val intent = Intent(requireActivity(), KeywordActivity::class.java)
            startActivity(intent)
        }

        // 툴바의 X버튼 눌렀을 때 동작
        binding.noticeToolbar.toolbarSearchTextClear.setOnClickListener {
            binding.noticeToolbar.toolbarSearchText.text.clear()
        }

        // 각각 탭 버튼 눌렀을 때 동작
        binding.noticeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                binding.noticeRvList.scrollToPosition(0)

                binding.noticeToolbar.toolbarSearchText.text.clear()
                if (tab.text == "대학") {
                    viewModel.setTabMode(true)
                } else {
                    viewModel.setTabMode(false)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {} // 구현 X

            // 다시 클릭시 스크롤되게 설정
            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.noticeRvList.smoothScrollToPosition(-10)
            }
        })
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
package com.dongyang.android.youdongknowme.ui.view.notice

import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.remote.entity.Notice
import com.dongyang.android.youdongknowme.databinding.FragmentNoticeBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.hideKeyboard
import com.dongyang.android.youdongknowme.standard.util.log
import com.dongyang.android.youdongknowme.standard.util.showKeyboard
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.tabs.TabLayout

/* 공지 사항 화면 */
class NoticeFragment : BaseFragment<FragmentNoticeBinding, NoticeViewModel>(), NoticeClickListener {

    companion object {
        fun newInstance() = NoticeFragment()
    }

    override val layoutResourceId: Int = R.layout.fragment_notice
    override val viewModel: NoticeViewModel by viewModel()

    // TODO :: 로컬 데이터에서 받아온 것을 DEFAULT 값으로 설정
    // TODO :: TAB 에서 공지사항임이 확인 됐을 경우에는 공지사항 코드로 고정해야 함
    val code = CODE.COMPUTER_SOFTWARE_ENGINE
    private lateinit var adapter: NoticeAdapter

    override fun initStartView() {
        binding.viewModel = viewModel
        adapter = NoticeAdapter().apply { setItemClickListener(this@NoticeFragment) }
        binding.noticeRvList.apply {
            this.adapter = this@NoticeFragment.adapter
            this.layoutManager = LinearLayoutManager(requireActivity())
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(requireActivity(), 1))
        }
    }

    override fun initDataBinding() {
        viewModel.noticeList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.searchList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.errorState.observe(viewLifecycleOwner) { resId ->
            showToast(getString(resId))
        }
    }

    override fun initAfterBinding() {
        // 최초 데이터 불러오기
        getNoticeList()
        // 새로고침 했을 때 동작
        binding.noticeSwipe.setOnRefreshListener {
            getNoticeList()
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
            val searchData = textView.text.toString()
            if (actionId == EditorInfo.IME_ACTION_SEARCH && searchData.isNotEmpty()) {
                textView.hideKeyboard()
                viewModel.getNoticeSearchList(code, searchData)
            }

            false
        }

        // 툴바의 X버튼 눌렀을 때 동작
        binding.noticeToolbar.toolbarSearchTextClear.setOnClickListener {
            binding.noticeToolbar.toolbarSearchText.text.clear()
        }

        // 각각 탭 버튼 눌렀을 때 동작
        binding.noticeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.text == "대학") {
                    // TODO :: 대학 공지사항 API 연동
                    val test = listOf(
                        Notice(
                            0,
                            "[공지] 3월 31일(목)부터 적용될 수업 방침에 대한 안내 말씀 드립니다.",
                            "2022-04-07",
                            "김지원"
                        )
                    )
                    adapter.submitList(test)
                } else {
                    getNoticeList()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {} // 구현 X
            override fun onTabReselected(tab: TabLayout.Tab?) {} // 구현 X
        })


    }

    private fun getNoticeList() {
        viewModel.getNoticeList(code)
    }

    // 아이템 클릭시 자세히 보기 화면으로 이동
    override fun itemClick(num: Int) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra("num", num)
        startActivity(intent)
    }
}
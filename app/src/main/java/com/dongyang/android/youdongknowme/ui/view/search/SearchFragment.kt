package com.dongyang.android.youdongknowme.ui.view.search

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentSearchBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.standard.util.dpToPx
import com.dongyang.android.youdongknowme.ui.adapter.NoticeAdapter
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import com.dongyang.android.youdongknowme.ui.view.util.hideKeyboard
import com.dongyang.android.youdongknowme.ui.view.util.showKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override val layoutResourceId: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()

    private lateinit var adapter: NoticeAdapter
    private var searchContent: String = ""

    override fun initStartView() {
        setupRecyclerViewMargin()
        setupRecyclerview()
        showKeyboardOnEditTextFocus()
        setupHideKeyboardOnOutsideTouch()
        setTextClearButtonVisibility()
        onSearchBtnClickListener()
        setTextClearButtonClickListener()
        setupInfinityScroll()
    }

    override fun initDataBinding() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.searchNotices.observe(viewLifecycleOwner) { searchNotices ->
            if (searchNotices.isNotEmpty()) {
                setupRecyclerViewMargin()
                adapter.submitList(searchNotices)

            }
            if (searchNotices.isNullOrEmpty()) {
                setupRecyclerViewMargin()
            }
        }

        viewModel.searchContent.observe(viewLifecycleOwner) { content ->
            searchContent = content
        }

        viewModel.errorState.observe(viewLifecycleOwner, EventObserver { resId ->
            showToast(getString(resId))
        })

        viewModel.noSearchResult.observe(viewLifecycleOwner) { noSearchResult ->
            if (noSearchResult) {
                binding.clSearchEmpty.visibility = View.VISIBLE
            } else {
                binding.clSearchEmpty.visibility = View.GONE
            }
        }
    }

    override fun initAfterBinding() = Unit

    private fun setupRecyclerViewMargin() {
        if (::adapter.isInitialized.not()) {
            val marginDp = SEARCH_RESULT_RECYCLERVIEW_MARGIN_TOP_FOR_TOUCH
            val marginPx = marginDp.dpToPx(requireContext())
            val layoutParams = binding.rvSearchResult.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = marginPx
            binding.rvSearchResult.layoutParams = layoutParams
        } else {
            val marginDp = SEARCH_RESULT_RECYCLERVIEW_MARGIN_TOP_DEFAULT
            val marginPx = marginDp.dpToPx(requireContext())
            val layoutParams = binding.rvSearchResult.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = marginPx
            binding.rvSearchResult.layoutParams = layoutParams
        }
    }

    private fun setupRecyclerview() {
        adapter = NoticeAdapter(onItemClick = { url -> navigateToDetail(url) })
        binding.rvSearchResult.apply {
            this.adapter = this@SearchFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = null
            setHasFixedSize(true)
        }
    }

    private fun showKeyboardOnEditTextFocus() {
        binding.etSearchBar.requestFocus()
        requireContext().showKeyboard(binding.etSearchBar)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupHideKeyboardOnOutsideTouch() {
        binding.root.setOnTouchListener { _, _ ->
            requireContext().hideKeyboard(binding.root)
            false
        }
    }

    private fun setTextClearButtonVisibility() {
        binding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable?) {
                viewModel.updateSearchContent(binding.etSearchBar.text.toString())
            }
        })

        viewModel.searchClearVisibility.observe(viewLifecycleOwner) { isValid ->
            binding.ivSearchClear.visibility = if (isValid) View.VISIBLE else View.GONE
        }
    }

    private fun setTextClearButtonClickListener() {
        binding.ivSearchClear.setOnClickListener {
            binding.etSearchBar.text.clear()
        }
    }

    private fun onSearchBtnClickListener() {
        binding.etSearchBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (validateSearchContentLength()) {
                    viewModel.fetchSearchNotices()
                    adapter.submitList(emptyList())
                } else {
                    binding.etSearchBar.text.clear()
                    showToast(getString(R.string.search_minimum))
                }
                requireContext().hideKeyboard(binding.root)
                true
            } else {
                false
            }
        }
    }

    private fun validateSearchContentLength(): Boolean {
        return searchContent.length >= SEARCH_CONTENT_MINIMUM_LENGTH
    }

    private fun navigateToDetail(url: String) {
        val intent = DetailActivity.newIntent(requireContext(), url)
        startActivity(intent)
    }

    private fun setupInfinityScroll() {
        binding.rvSearchResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!viewModel.isLoading.value!! && lastVisibleItemPosition >= totalItemCount - 1) {
                    viewModel.fetchSearchNotices()
                }
            }
        })
    }

    companion object {
        private const val SEARCH_RESULT_RECYCLERVIEW_MARGIN_TOP_DEFAULT = 8
        private const val SEARCH_RESULT_RECYCLERVIEW_MARGIN_TOP_FOR_TOUCH = 800
        private const val SEARCH_CONTENT_MINIMUM_LENGTH = 2
    }
}
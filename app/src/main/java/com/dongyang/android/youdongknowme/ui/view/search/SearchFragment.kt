package com.dongyang.android.youdongknowme.ui.view.search

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentSearchBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import com.dongyang.android.youdongknowme.ui.view.util.hideKeyboard
import com.dongyang.android.youdongknowme.ui.view.util.showKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override val layoutResourceId: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()

    override fun initStartView() {
        setupUI()
        setTextClearButtonClickListener()
    }

    private fun setupUI() {
        showKeyboardOnEditTextFocus()
        setupHideKeyboardOnOutsideTouch()
        setTextClearButtonVisibility()
        onDoneBtnClickListener()
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
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                Unit

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

    private fun onDoneBtnClickListener() {
        binding.etSearchBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.fetchSearchNotices()
                true
            } else {
                false
            }
        }
    }

    override fun initDataBinding() {
        viewModel.searchNotices.observe(viewLifecycleOwner) {response ->
            Log.d("test123", response.toString())
        }

    }

    override fun initAfterBinding() {
    }
}
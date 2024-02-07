package com.dongyang.android.youdongknowme.ui.view.search

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.FragmentSearchBinding
import com.dongyang.android.youdongknowme.standard.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    override val layoutResourceId: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()

    override fun initStartView() {
        binding.searchViewModel = viewModel
    }

    override fun initDataBinding() {
        setupUI()
        setTextClearButtonClickListener()
    }

    private fun setupUI() {
        showKeyboardOnEditTextFocus()
        setupHideKeyboardOnOutsideTouch()
        setTextClearButtonVisibility()
    }

    private fun showKeyboardOnEditTextFocus() {
        binding.etSearchBar.requestFocus()

        binding.etSearchBar.post {
            binding.etSearchBar.setSelection(binding.etSearchBar.text.length)
        }

        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.etSearchBar, InputMethodManager.SHOW_IMPLICIT)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupHideKeyboardOnOutsideTouch() {
        binding.root.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setTextClearButtonVisibility() {
        binding.etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.validateSearchClearButtonVisibility()
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

    override fun initAfterBinding() {
    }

}
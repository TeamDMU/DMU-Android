package com.dongyang.android.youdongknowme.ui.view.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDetailBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_detail
    override val viewModel: DetailViewModel by viewModel()

    @SuppressLint("SetJavaScriptEnabled")
    override fun initStartView() {
        val url = intent.getStringExtra(KEY_URL)
        with(binding.detailWvNotice) {
            settings.javaScriptEnabled = true

            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()

            loadUrl(url.toString())
        }
    }

    override fun initDataBinding() {
        viewModel.isLoading.observe(this) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.errorState.observe(this, EventObserver { resId ->
            showToast(getString(resId))
        })
    }

    override fun initAfterBinding() = Unit

    override fun onBackPressed() {
        if (binding.detailWvNotice.canGoBack()) {
            binding.detailWvNotice.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val KEY_URL = "url"

        fun newIntent(context: Context, url: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_URL, url)
            }
        }
    }
}
package com.dongyang.android.youdongknowme.ui.view.detail

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
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

            setDownloadListener { url, user, contentDisposition, mimetype, contentLength ->
                val request = DownloadManager.Request(Uri.parse(url)).apply {
                    // 다운로드에 대한 제목, 설명 설정
                    setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype))
                    setDescription("Downloading file...")

                    // 알림 표시줄에 다운로드 진행 상태 표시
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

                    // 파일 다운로드 경로 설정
                    setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        URLUtil.guessFileName(url, contentDisposition, mimetype)
                    )

                    // 모바일 네트워크 및 와이파이에서 다운로드 허용 설정
                    setAllowedOverMetered(true)
                    setAllowedOverRoaming(true)
                }

                // DownloadManager를 사용하여 다운로드 요청
                val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
            }

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
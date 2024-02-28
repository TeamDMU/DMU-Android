package com.dongyang.android.youdongknowme.ui.view.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDetailBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.view.util.EventObserver
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLDecoder

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

            setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
                try {
                    val request = DownloadManager.Request(Uri.parse(url))
                    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                    val fileName = URLDecoder.decode(contentDisposition, "UTF-8")
                        .replace("attachment; filename=", "")
                        .let {
                            var tempName = it
                            val idxFileName = tempName.indexOf("filename =")
                            if (idxFileName > -1) {
                                tempName = tempName.substring(idxFileName + 9).trim()
                            }

                            tempName.removeSuffix(";").trim('"')
                        }

                    // 세션 유지를 위해 쿠키 세팅하기
                    val cookie = CookieManager.getInstance().getCookie(url)
                    request.addRequestHeader("Cookie", cookie)

                    request.setMimeType(mimetype)
                    request.addRequestHeader("User-Agent", userAgent)
                    request.setDescription("Downloading File")
                    request.setAllowedOverMetered(true)
                    request.setAllowedOverRoaming(true)
                    request.setTitle(fileName)
                    request.setRequiresCharging(false)

                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    request.setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        fileName
                    )

                    downloadManager.enqueue(request)
                    Toast.makeText(applicationContext, R.string.detail_download_description, Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    if (ContextCompat.checkSelfPermission(
                            applicationContext,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            this@DetailActivity,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            1004
                        )
                    } else {
                        Toast.makeText(baseContext, R.string.detail_download_permission, Toast.LENGTH_LONG)
                            .show()
                    }
                }
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
        private const val KEY_URL = "url"

        fun newIntent(context: Context, url: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(KEY_URL, url)
            }
        }
    }
}
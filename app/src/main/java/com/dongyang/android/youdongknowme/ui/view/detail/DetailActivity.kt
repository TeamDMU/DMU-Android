package com.dongyang.android.youdongknowme.ui.view.detail

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDetailBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.standard.util.logd
import com.dongyang.android.youdongknowme.ui.adapter.FileAdapter
import com.dongyang.android.youdongknowme.ui.adapter.ImageAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

/* 공지사항 글 상세 화면 */
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(), DetailClickListener {

    override val layoutResourceId: Int = R.layout.activity_detail
    override val viewModel: DetailViewModel by viewModel()

    private lateinit var fileAdapter: FileAdapter
    private lateinit var imageAdapter: ImageAdapter

    private val departCode: Int by lazy { intent.getIntExtra("departCode", 0) }
    private val boardNum: Int by lazy { intent.getIntExtra("boardNum", 0) }

    private val mDownloadManager: DownloadManager by lazy {
        this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
    private var mDownloadQueueId: Long = 0

    override fun initStartView() {
        viewModel.setNoticeDetailInfo(departCode, boardNum)

        fileAdapter = FileAdapter().apply { setItemClickListener(this@DetailActivity) }
        binding.detailFileRcv.apply {
            this.adapter = fileAdapter
            this.layoutManager = LinearLayoutManager(this@DetailActivity)
            this.setHasFixedSize(true)
        }

        imageAdapter = ImageAdapter()
        binding.detailImageRcv.apply {
            this.adapter = imageAdapter
            this.layoutManager = LinearLayoutManager(this@DetailActivity)
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        binding.viewModel = viewModel

        viewModel.isLoading.observe(this) {
            if (it) showLoading()
            else dismissLoading()
        }

        viewModel.errorState.observe(this) { resId ->
            showToast(getString(resId))
        }

        viewModel.fileUrl.observe(this) {
            if (it.isNotEmpty())
                fileAdapter.submitList(it)
        }

        viewModel.imgUrl.observe(this) {
            if (it.isNotEmpty())
                imageAdapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
        viewModel.getNoticeDetail()

        // 뒤로가기 버튼 클릭 시
        binding.detailExit.setOnClickListener {
            finish()
        }
    }

    // 파일 클릭했을 때 동작
    override fun fileClick(fileName: String, fileUri: String) {

        val filePath =
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS +
                        "/${R.string.app_name}" + "/$fileName"
            )

        val outputFile: File = filePath
        if (outputFile.parentFile?.exists() == false) {
            outputFile.parentFile?.mkdirs()
        }

        val fileUrl = Uri.parse(fileUri)
        val request = DownloadManager.Request(fileUrl)
            .setTitle(fileName)
            .setDescription("${R.string.notice_detail_download_description}")
            .setDestinationUri(Uri.fromFile(filePath))
            .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        mDownloadQueueId = mDownloadManager.enqueue(request)

        // 예외처리
        val intentFilter = IntentFilter()
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)

        val onDownloadComplete = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent.action) {
                    if (mDownloadQueueId == id) {
                        val query: DownloadManager.Query = DownloadManager.Query()
                        query.setFilterById(id)
                        val cursor = mDownloadManager.query(query)
                        if (cursor.moveToFirst()) {
                            val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                            val status = cursor.getInt(columnIndex)
                            if (status == DownloadManager.STATUS_FAILED) {
                                Toast.makeText(
                                    context,
                                    "${R.string.notice_detail_download_fail}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }

        registerReceiver(onDownloadComplete, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDownloadManager.remove(mDownloadQueueId)
    }
}
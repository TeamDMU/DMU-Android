package com.dongyang.android.youdongknowme.ui.view.detail

import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDetailBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
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
        binding.vm = viewModel

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
        viewModel.fetchNoticeDetail()

        // 뒤로가기 버튼 클릭 시
        binding.detailExitBtn.setOnClickListener {
            finish()
        }
    }

    // 파일 클릭했을 때 동작
    override fun fileClick(fileName: String, fileUri: String) {
        openDownloadListener(fileName, fileUri)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDownloadManager.remove(mDownloadQueueId)
    }

    private fun openDownloadListener(fileName: String, fileUri: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                // 사용자가 권한 부여를 완료한 경우
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                -> {
                    setDownloadManager(fileName, fileUri)
                }
                // 사용자가 명시적으로 권한 부여를 거부한 경우
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                -> {
                    showDialogToGetPermission()
                }
                // 사용자에게 최초로 권한을 요청하는 경우
                else
                -> {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
                }
            }
        }
    }

    private fun setDownloadManager(fileName: String, fileUri: String) {
        val filePath =
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS +
                        "/${getString(R.string.app_name)}" + "/$fileName"
            )

        val outputFile: File = filePath
        if (outputFile.parentFile?.exists() == false) {
            outputFile.parentFile?.mkdirs()
        }

        val fileUrl = Uri.parse(fileUri)
        val request = DownloadManager.Request(fileUrl)
            .setTitle(fileName)
            .setDescription("${R.string.detail_download_description}")
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
                            when(status) {
                                DownloadManager.STATUS_FAILED -> {
                                    Toast.makeText(
                                        context,
                                        getString(R.string.detail_download_fail),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                DownloadManager.STATUS_SUCCESSFUL -> {
                                    Toast.makeText(
                                        context,
                                        getString(R.string.detail_download_success),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }

        registerReceiver(onDownloadComplete, intentFilter)
    }

    private fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.detail_write_permission_dialog_title))
            .setMessage(getString(R.string.detail_write_permission_dialog_message))

        builder.setPositiveButton(getString(R.string.detail_write_permission_dialog_ok)) { _, _ ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", "com.dongyang.android.youdongknowme", null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton(getString(R.string.detail_write_permission_dialog_no)) { _, _ ->
            // 거부
        }
        val dialog = builder.create()
        dialog.show()
    }
}
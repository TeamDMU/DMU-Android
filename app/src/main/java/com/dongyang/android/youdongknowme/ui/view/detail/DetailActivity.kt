package com.dongyang.android.youdongknowme.ui.view.detail

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDetailBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.standard.util.log
import com.dongyang.android.youdongknowme.ui.adapter.FileAdapter
import com.dongyang.android.youdongknowme.ui.adapter.ImageAdapter
import java.io.File

/* 공지사항 글 상세 화면 */
class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    override val layoutResourceId: Int = R.layout.activity_detail
    override val viewModel: DetailViewModel by viewModel()

    private lateinit var fileAdapter: FileAdapter
    private lateinit var imageAdapter: ImageAdapter

    private val num : Int by lazy {
        intent.getIntExtra("num", 0)
    }

    // TODO :: 로컬 데이터에서 받아온 것을 DEFAULT 값으로 설정
    private val code = CODE.COMPUTER_SOFTWARE_ENGINE_CODE

    override fun initStartView() {
        fileAdapter = FileAdapter()
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

        viewModel.errorState.observe(this) { resId ->
            showToast(getString(resId))
        }

        viewModel.fileUrl.observe(this) {
            if(it.isNotEmpty())
                fileAdapter.submitList(it)
        }

        viewModel.imgUrl.observe(this) {
            if(it.isNotEmpty())
                imageAdapter.submitList(it)
        }
    }

    override fun initAfterBinding() {
        viewModel.getNoticeList(code, num)

        // 뒤로가기 버튼 클릭 시
        binding.detailExit.setOnClickListener {
            finish()
        }

        log(num.toString())
    }
}
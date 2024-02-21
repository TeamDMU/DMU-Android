package com.dongyang.android.youdongknowme.ui.view.license

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.data.local.entity.OpenSourceEntity
import com.dongyang.android.youdongknowme.databinding.ActivityLicenseBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import com.dongyang.android.youdongknowme.ui.adapter.LicenseAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class LicenseActivity : BaseActivity<ActivityLicenseBinding, LicenseViewModel>(),
    LicenseClickListener {
    override val layoutResourceId: Int = R.layout.activity_license
    override val viewModel: LicenseViewModel by viewModel()

    lateinit var adapter: LicenseAdapter

    override fun initStartView() {
        adapter = LicenseAdapter().apply { setItemClickListener(this@LicenseActivity) }
        binding.licenseRcv.apply {
            this.adapter = this@LicenseActivity.adapter
            this.layoutManager = LinearLayoutManager(this@LicenseActivity)
            this.addItemDecoration(DividerItemDecoration(this@LicenseActivity, 1))
            this.setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {}

    override fun initAfterBinding() {
        adapter.submitList(OpenSourceEntity.openSourceList)

        binding.licenseToolbar.btnToolbarExit.setOnClickListener { finish() }
    }

    override fun itemClick(openSourceEntity: OpenSourceEntity) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(openSourceEntity.url))
        startActivity(intent)
    }
}

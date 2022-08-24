package com.dongyang.android.youdongknowme.ui.view.license

import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityLicenseBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LicenseActivity : BaseActivity<ActivityLicenseBinding, LicenseViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_license
    override val viewModel: LicenseViewModel by viewModel()

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}

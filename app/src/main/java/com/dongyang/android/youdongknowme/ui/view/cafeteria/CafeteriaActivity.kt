package com.dongyang.android.youdongknowme.ui.view.cafeteria

import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityCafeteriaBinding
import com.dongyang.android.youdongknowme.standard.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CafeteriaActivity : BaseActivity<ActivityCafeteriaBinding, CafeteriaViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_cafeteria
    override val viewModel: CafeteriaViewModel by viewModel()

    override fun initStartView() {
        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewModel.menuList.observe(this) {

        }
    }

    override fun initAfterBinding() {
        viewModel.fetchCafeteria()
    }

}
package com.dongyang.android.youdongknowme.standard.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel> : AppCompatActivity() {

    lateinit var binding: T
    abstract val layoutResourceId: Int
    abstract val viewModel: R


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initStartView()
        connectionCheck()
        initDataBinding()
        initAfterBinding()
    }

    protected fun showToast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    protected fun showLoading() {
        TODO()
    }

    protected fun dismissLoading() {
        TODO()
    }

    protected fun configureToolbar() {
        TODO()
    }

    private fun connectionCheck() {
        viewModel.errorConnectionState.observe(this) { resId ->
            showToast(getString(resId))
        }
    }

    /**
     * 레이아웃을 띄운 직후 호출.
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰.
     */

    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */

    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */

    abstract fun initAfterBinding()

}
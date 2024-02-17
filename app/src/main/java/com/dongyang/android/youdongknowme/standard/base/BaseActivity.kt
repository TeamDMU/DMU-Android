package com.dongyang.android.youdongknowme.standard.base

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dongyang.android.youdongknowme.ui.view.LoadingDialog
import com.dongyang.android.youdongknowme.ui.view.util.toast

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel> : AppCompatActivity() {

    lateinit var binding: T
    abstract val layoutResourceId: Int
    abstract val viewModel: R

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }
    protected fun setSpan(spanTextView: TextView, startIdx: Int, endIdx: Int){
        SpannableStringBuilder(spanTextView.text).apply {
            setSpan(
                ForegroundColorSpan(getColor(com.dongyang.android.youdongknowme.R.color.main)),
                startIdx,
                endIdx,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spanTextView.text = this
        }
    }
    protected fun showToast(message: String) {
        applicationContext.toast(message)
    }


    protected fun showLoading() {
        loadingDialog.show()
    }

    protected fun dismissLoading() {
        loadingDialog.dismiss()
    }

    protected fun configureToolbar() {
        TODO()
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
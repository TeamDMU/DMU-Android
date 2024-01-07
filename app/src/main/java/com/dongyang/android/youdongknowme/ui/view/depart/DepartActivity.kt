package com.dongyang.android.youdongknowme.ui.view.depart

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDepartBinding
import com.dongyang.android.youdongknowme.ui.adapter.DepartAdapter
import com.dongyang.android.youdongknowme.ui.view.keyword.KeywordActivity
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DepartActivity : AppCompatActivity(), DepartClickListener {

    private lateinit var adapter: DepartAdapter
    private lateinit var binding: ActivityDepartBinding
    private val viewModel: DepartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vm = viewModel

        viewModel.checkFirstLaunch()

        // 부분 색상 지정
        spanText(binding.departTitleMain,0, 5)
        
        // 학과 리스트
        val items =
            resources.getStringArray(R.array.dmu_department_list).toCollection(ArrayList<String>())
        items.sort()

        adapter = DepartAdapter().apply {
            submitList(items)
            setItemClickListener(this@DepartActivity)
        }

        binding.departRcv.apply {
            this.adapter = this@DepartActivity.adapter
            this.layoutManager = LinearLayoutManager(this@DepartActivity)
            this.setHasFixedSize(true)
        }

        // 선택 포지션을 실시간 옵저빙
        viewModel.selectDepartPosition.observe(this) {
            adapter.submitPosition(it)

            // 포지션 선택 시 스낵바를 통해 알림 표시
            if (it != -1) getSnackBar(items).show()
        }
    }

    private fun spanText(spanTextView: TextView, startIdx: Int, endEdx: Int){
        val ssb = SpannableStringBuilder(spanTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.main)),
            startIdx,
            endEdx,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanTextView.text = ssb
    }

    // 컨테이너 클릭 시 선택한 학과의 포지션 저장
    override fun containerClick(position: Int) {
        viewModel.setSelectPosition(position)
    }

    // 확인 버튼을 누르면 내부 DB에 학과를 담고 메인 액티비티로 이동
    private fun getSnackBar(items: ArrayList<String>): Snackbar {
        val snackbar =
            Snackbar.make(binding.departContainer, getString(R.string.department_snackbar_title), LENGTH_LONG)
                .setAction(getString(R.string.department_snackbar_positive_button)) {
                    viewModel.setDepartment(items[viewModel.selectDepartPosition.value ?: 0])
                    if(viewModel.isFirstLaunch.value == true) {
                        val intent = Intent(this, KeywordActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onShown(transientBottomBar: Snackbar?) {
                        super.onShown(transientBottomBar)

                        binding.departRcv.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            setMargins(0, 0, 0, transientBottomBar?.view?.height ?: 0)
                        }
                    }

                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)

                        binding.departRcv.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            setMargins(0, 0, 0, 0)
                        }
                    }
                })


        val snackBarView = snackbar.view
        val snackBarText = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackBarText.typeface = Typeface.createFromAsset(this.assets, "pretendard_regular.otf")

        snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE

        return snackbar
    }
}
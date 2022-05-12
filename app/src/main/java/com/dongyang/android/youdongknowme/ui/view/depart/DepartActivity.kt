package com.dongyang.android.youdongknowme.ui.view.depart

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityDepartBinding
import com.dongyang.android.youdongknowme.ui.adapter.DepartAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DepartActivity : AppCompatActivity() {

    private lateinit var adapter: DepartAdapter
    private lateinit var binding: ActivityDepartBinding
    private val viewModel: DepartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = resources.getStringArray(R.array.dmu_department_list).toCollection(ArrayList<String>())
        item.sort()

        adapter = DepartAdapter().apply { submitList(item) }
        binding.departRcv.apply {
            this.adapter = this@DepartActivity.adapter
            this.layoutManager = LinearLayoutManager(this@DepartActivity)
            this.setHasFixedSize(true)
        }

//        binding.departComputerSoftware.setOnClickListener {
//            viewModel.setDepartment(binding.departComputerSoftwareTv.text.toString())
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }
}
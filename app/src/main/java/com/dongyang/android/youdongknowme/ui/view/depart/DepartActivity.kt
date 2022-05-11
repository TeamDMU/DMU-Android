package com.dongyang.android.youdongknowme.ui.view.depart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongyang.android.youdongknowme.databinding.ActivityDepartBinding
import com.dongyang.android.youdongknowme.ui.view.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DepartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDepartBinding
    private val viewModel: DepartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.departComputerSoftware.setOnClickListener {
            viewModel.setDepartment(binding.departComputerSoftwareTv.text.toString())
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
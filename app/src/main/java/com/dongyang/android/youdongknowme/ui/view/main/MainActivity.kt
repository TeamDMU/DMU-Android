package com.dongyang.android.youdongknowme.ui.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.databinding.ActivityMainBinding
import com.dongyang.android.youdongknowme.ui.view.detail.DetailActivity
import com.dongyang.android.youdongknowme.ui.view.notice.NoticeFragment

/* 메인 액티비티 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, NoticeFragment.newInstance())
            .commit()
    }
}
package com.dongyang.android.youdongknowme.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.dongyang.android.youdongknowme.R
import com.dongyang.android.youdongknowme.ui.detail.DetailActivity

/* 메인 화면 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testCode = findViewById<TextView>(R.id.main_title)

        testCode.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }
}
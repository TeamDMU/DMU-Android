package com.dongyang.android.youdongknowme.ui.view.setting

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dongyang.android.youdongknowme.databinding.DialogPermissionBinding

class PermissionDialog(val title: String, val content: String, val pacakageName: String) : DialogFragment() {
    private var _binding: DialogPermissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogPermissionBinding.inflate(inflater, container, false)
        val view = binding.root

        // 레이아웃 배경을 투명하게
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 제목, 내용 설정
        binding.tvDialogPermissionTitle.text = title
        binding.tvDialogPermissionContent.text = content

        // 취소 버튼
        binding.tvDialogPermissionCancle.setOnClickListener {
            dismiss()
        }
        // 확인 버튼
        binding.tvDialogPermissionComplete.setOnClickListener {
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:$pacakageName"))
            startActivity(intent)
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.dongyang.android.youdongknowme.ui.view.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.dongyang.android.youdongknowme.databinding.DialogDatepickerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate

class DatePickerDialog(
    val year: Int,
    val month: Int,
    private val dateSelectedListener: ScheduleFragment,
    private val cancelListener: (() -> Unit)? = null,
) : DialogFragment() {

    private var _binding: DialogDatepickerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScheduleViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        
        _binding = DialogDatepickerBinding.inflate(inflater, container, false)
        val view = binding.root

        // 현재 연도
        val currentYear = LocalDate.now().year

        // 순환 막기
        binding.numberpickerDialogDatepickerYear.wrapSelectorWheel = false
        binding.numberpickerDialogDatepickerMonth.wrapSelectorWheel = false

        // editText 설정 막기
        binding.numberpickerDialogDatepickerYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.numberpickerDialogDatepickerMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        // 연도 및 월의 최소/최대값 설정
        with(binding.numberpickerDialogDatepickerYear) {
            minValue = currentYear - 1
            maxValue = currentYear + 1
        }

        with(binding.numberpickerDialogDatepickerMonth) {
            minValue = 1
            maxValue = if (year == currentYear + 1) 2 else 12

            // 연도 선택에 따른 월의 최대값 동적 설정
            binding.numberpickerDialogDatepickerYear.setOnValueChangedListener { _, _, newYear ->
                maxValue = if (newYear == currentYear + 1) 2 else 12
            }
        }

        // 초기 설정
        binding.numberpickerDialogDatepickerYear.value = year
        binding.numberpickerDialogDatepickerMonth.value = month

        // 취소 버튼 클릭시
        binding.tvDialogDatepickerCancel.setOnClickListener {
            cancelListener?.invoke()
            dismiss()
        }

        binding.tvDialogPermissionComplete.setOnClickListener {
            viewModel.setDatePicker(binding.numberpickerDialogDatepickerYear.value, binding.numberpickerDialogDatepickerMonth.value)
            dateSelectedListener.onDateSelected(binding.numberpickerDialogDatepickerYear.value, binding.numberpickerDialogDatepickerMonth.value)
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
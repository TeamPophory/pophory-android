package com.teampophory.pophory.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teampophory.pophory.databinding.ModalBottomSheetContentBinding

class ModalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListMenuViews()
    }

    private fun initListMenuViews() {
        with(binding) {
            tvSortNewest.setOnClickListener {
                dismissAllowingStateLoss()
            }
            tvSortOldest.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
package com.teampophory.pophory.feature.signup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.databinding.FragmentSignUpDialogBinding

class SignUpDialogFragment : DialogFragment() {

    private var _binding: FragmentSignUpDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게 해줌
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDialog.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        context?.let {
            dialogWidthPercent(it, dialog)
        }
    }
    private fun dialogWidthPercent(context: Context, dialog: Dialog?, percent: Double = 0.8) {
        val deviceSize = getDeviceSize(context)
        dialog?.window?.run {
            val params = attributes
            params.width = (deviceSize[0] * percent).toInt()
            attributes = params
        }
    }

    private fun getDeviceSize(context: Context): IntArray {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val windowInsets = windowMetrics.windowInsets

            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars() or WindowInsets.Type.displayCutout()
            )
            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom

            val bounds = windowMetrics.bounds

            return intArrayOf(bounds.width() - insetsWidth, bounds.height() - insetsHeight)
        } else {
            val display = windowManager.defaultDisplay
            val size = Point()

            display?.getSize(size)

            return intArrayOf(size.x, size.y)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
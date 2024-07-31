package com.teampophory.pophory.designsystem.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.teampophory.pophory.common.context.dialogWidthPercent
import com.teampophory.pophory.common.view.setOnSingleClickListener
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.designsystem.R
import com.teampophory.pophory.designsystem.databinding.DialogCommonOneButtonBinding

class ForceUpdateDialog : DialogFragment() {

    private val binding by viewBinding(DialogCommonOneButtonBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.dialog_common_one_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initButtonListener()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogWidthPercent(dialog)
        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        isCancelable = false
    }

    private fun initViews() {
        val title = "업데이트가 필요해요"
        val description = "원활한 이용을 위해\n최신버전으로 업데이트 해주세요."
        val imageResId = R.drawable.ic_customizing_done
        val buttonText = "확인"

        with(binding) {
            tvDialogTitle.text = title
            tvDialogDescription.text = description
            ivDialogIcon.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), imageResId)
            )
            ivDialogIcon.isVisible = true
            tvButton.text = buttonText
        }
    }

    private fun initButtonListener() {
        binding.tvButton.setOnSingleClickListener {
            val packageName = requireContext().packageName
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            } else {
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
                startActivity(webIntent)
            }
            dismiss()
        }
    }

    companion object {
        const val TAG = "ForceUpdateDialog"

        fun newInstance(): ForceUpdateDialog {
            return ForceUpdateDialog()
        }
    }
}

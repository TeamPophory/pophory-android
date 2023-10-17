package com.teampophory.pophory.util.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.teampophory.pophory.common.context.dialogWidthPercent
import com.teampophory.pophory.common.view.setOnSingleClickListener
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.DialogCommonTwoButtonBinding

class TwoButtonCommonDialog : DialogFragment() {

    private val binding by viewBinding(DialogCommonTwoButtonBinding::bind)

    private var confirmButtonClickListener: (() -> Unit)? = null
    private var dismissButtonClickListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogCommonTwoButtonBinding.inflate(inflater, container, false).root
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
    }

    fun setConfirmButtonClickListener(confirmButtonClickListener: () -> Unit) {
        this.confirmButtonClickListener = confirmButtonClickListener
    }

    fun setDismissButtonClickListener(dismissButtonClickListener: () -> Unit) {
        this.dismissButtonClickListener = dismissButtonClickListener
    }

    private fun initViews() {
        val title = arguments?.getString(TITLE, "")
        val description = arguments?.getString(DESCRIPTION, "")
        val imageResId = arguments?.getInt(IMAGE_RES_ID)
        val confirmButtonText = arguments?.getString(CONFIRM_BUTTON_TEXT, "")
        val dismissButtonText = arguments?.getString(DISMISS_BUTTON_TEXT, "")

        with(binding) {
            tvDialogTitle.text = title
            tvDialogDescription.text = description
            ivDialogIcon.setImageDrawable(imageResId?.let {
                ivDialogIcon.isVisible = true
                ContextCompat.getDrawable(requireContext(), it)
            })
            tvConfirmButton.text = confirmButtonText
            tvDismissButton.text = dismissButtonText
        }
    }

    private fun initButtonListener() {
        binding.tvConfirmButton.setOnSingleClickListener {
            confirmButtonClickListener?.invoke()
            dismiss()
        }
        binding.tvDismissButton.setOnSingleClickListener {
            dismissButtonClickListener?.invoke()
            dismiss()
        }
    }

    companion object {
        const val TAG = "TwoButtonCommonDialog"

        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val IMAGE_RES_ID = "imageResId"
        const val CONFIRM_BUTTON_TEXT = "confirmButtonText"
        const val DISMISS_BUTTON_TEXT = "dismissButtonText"


        fun newInstance(
            title: String,
            description: String,
            @DrawableRes
            imageResId: Int,
            confirmButtonText: String,
            dismissButtonText: String
        ): TwoButtonCommonDialog {
            return TwoButtonCommonDialog().apply {
                arguments = Bundle().apply {
                    putString(TITLE, title)
                    putString(DESCRIPTION, description)
                    putInt(IMAGE_RES_ID, imageResId)
                    putString(CONFIRM_BUTTON_TEXT, confirmButtonText)
                    putString(DISMISS_BUTTON_TEXT, dismissButtonText)
                }
            }
        }
    }
}

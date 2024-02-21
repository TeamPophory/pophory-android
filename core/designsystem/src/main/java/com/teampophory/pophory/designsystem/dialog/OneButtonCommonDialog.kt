package com.teampophory.pophory.designsystem.dialog

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
import com.teampophory.pophory.designsystem.R
import com.teampophory.pophory.designsystem.databinding.DialogCommonOneButtonBinding

class OneButtonCommonDialog : DialogFragment() {

    private val binding by viewBinding(DialogCommonOneButtonBinding::bind)

    private var buttonClickListener: (() -> Unit)? = null

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
    }

    fun setButtonClickListener(buttonClickListener: () -> Unit) {
        this.buttonClickListener = buttonClickListener
    }

    private fun initViews() {
        val title = arguments?.getString("title", "")
        val description = arguments?.getString("description", "")
        val imageResId = arguments?.getInt("imageResId")
        val buttonText = arguments?.getString("buttonText", "")

        with(binding) {
            tvDialogTitle.text = title
            tvDialogDescription.text = description
            ivDialogIcon.setImageDrawable(
                imageResId?.let {
                    ivDialogIcon.isVisible = true
                    ContextCompat.getDrawable(requireContext(), it)
                },
            )
            tvButton.text = buttonText
        }
    }

    private fun initButtonListener() {
        binding.tvButton.setOnSingleClickListener {
            buttonClickListener?.invoke()
            dismiss()
        }
    }

    companion object {
        const val TAG = "OneButtonCommonDialog"
        fun newInstance(
            title: String,
            description: String,
            @DrawableRes imageResId: Int,
            buttonText: String,
        ): OneButtonCommonDialog {
            OneButtonCommonDialog().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("description", description)
                    putInt("imageResId", imageResId)
                    putString("buttonText", buttonText)
                }
            }.also {
                return it
            }
        }
    }
}

package com.teampophory.pophory.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.colorOf
import com.teampophory.pophory.common.primitive.textAppearance
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentSignUpFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpFirstFragment : Fragment() {

    private val binding by viewBinding(FragmentSignUpFirstBinding::bind)
    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentSignUpFirstBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvErrorMessage.isVisible = false
        binding.btnDeleteEditText.isGone = true
        setSpannableString()
        // edittext 상태
        setEditText()
        // edittext 삭제 버튼
        deleteAllEditText()
        signUpViewModel.setButtonState(false)
    }

    private fun deleteAllEditText() {
        binding.btnDeleteEditText.setOnClickListener {
            binding.editTvName.text.clear()
        }
    }

    private fun setEditText() {
        // 텍스트창 활성화
        binding.editTvName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                // 포커스가 주어졌을 때
                if (hasFocus) {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_selected)
                } else {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_default)
                }
            }
            // edittext text 변화 감지
            doAfterTextChanged {
                signUpViewModel.setRealName(it.toString())
                binding.btnDeleteEditText.isGone = it?.isEmpty() == true
                // 글자 수 계산
                binding.tvTextCount.text = "(${it.toString().length}/6)"

                val matcher = HANGUL_REGEX.matcher(binding.editTvName.text)
                if (!matcher.find()) {
                    binding.tvErrorMessage.text = "현재 한국어만 지원하고 있어요."
                    setEditTextState(
                        R.drawable.bg_sign_up_edit_text_error,
                        errorMessageState = true,
                        buttonState = false,
                    )
                } else if (it.toString().length < 2 || it.toString().length > 6) {
                    binding.tvErrorMessage.text = "2-6글자 이내로 작성해주세요."
                    setEditTextState(
                        R.drawable.bg_sign_up_edit_text_error,
                        errorMessageState = true,
                        buttonState = false,
                    )
                } else {
                    setEditTextState(
                        R.drawable.bg_sign_up_edit_text_selected,
                        errorMessageState = false,
                        buttonState = true,
                    )
                }
            }
        }
    }

    private fun setEditTextState(
        @DrawableRes background: Int,
        errorMessageState: Boolean,
        buttonState: Boolean,
    ) {
        binding.editTvName.setBackgroundResource(background)
        binding.tvErrorMessage.isVisible = errorMessageState
        signUpViewModel.setButtonState(buttonState)
    }

    private fun setSpannableString() {
        val fullText = getString(R.string.sign_up_first_title)
        val coloredText = "너의 이름" // 색상을 변경하려는 특정 단어
        val splittedText = fullText.split(coloredText)

        val text = buildSpannedString {
            append(splittedText.getOrNull(0))
            color(colorOf(com.teampophory.pophory.designsystem.R.color.pophory_purple)) {
                textAppearance(requireContext(), com.teampophory.pophory.designsystem.R.style.TextAppearance_Pophory_HeadLineBold) {
                    append(coloredText)
                }
            }
            append(splittedText[1])
        }
        binding.tvTitle.text = text
    }

    companion object {
        private const val HANGUL_PATTERN = "^[가-힣]*\$"
        val HANGUL_REGEX: Pattern = Pattern.compile(HANGUL_PATTERN)
    }
}

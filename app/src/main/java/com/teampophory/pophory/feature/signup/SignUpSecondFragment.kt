package com.teampophory.pophory.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.teampophory.pophory.databinding.FragmentSignUpSecondBinding
import com.teampophory.pophory.feature.auth.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpSecondFragment : Fragment() {
    private val binding by viewBinding(FragmentSignUpSecondBinding::bind)
    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentSignUpSecondBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvErrorMessage.isVisible = false
        binding.btnDeleteEditText.isGone = true
        setEditText()
        // edittext 삭제 버튼
        deleteAllEditText()
        setSpannableString()

        signUpViewModel.setButtonState(false)
    }

    private fun deleteAllEditText() {
        binding.btnDeleteEditText.setOnClickListener {
            binding.editTvId.text.clear()
        }
    }

    private fun setEditText() {
        // 텍스트창 활성화
        binding.editTvId.apply {
            setOnFocusChangeListener { _, hasFocus ->
                // 포커스가 주어졌을 때
                if (hasFocus) {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_selected)
                } else {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_default)
                }
            }
            doAfterTextChanged {
                signUpViewModel.setNickName(it.toString())
                // X버튼 생성 여부
                binding.btnDeleteEditText.isGone = it?.isEmpty() == true
                // 글자 수 계산
                binding.tvTextCount.text = "(${it.toString().length}/12)"

                val textMatcher = HANGUL_REGEX.matcher(binding.editTvId.text)
                if (!textMatcher.find()) {
                    binding.tvErrorMessage.text = "*올바른 형식의 아이디가 아닙니다"
                    setEditTextState(
                        R.drawable.bg_sign_up_edit_text_error,
                        errorMessageState = true,
                        buttonState = false,
                    )
                } else if (it.toString().length < 4 || it.toString().length > 12) {
                    binding.tvErrorMessage.text = "4-12글자 이내로 작성해주세요."
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
        background: Int,
        errorMessageState: Boolean,
        buttonState: Boolean,
    ) {
        binding.editTvId.setBackgroundResource(background)
        binding.tvErrorMessage.isVisible = errorMessageState
        signUpViewModel.setButtonState(buttonState)
    }

    private fun setSpannableString() {
        val fullText = getString(R.string.sign_up_second_title)
        val coloredText = "포포리 아이디" // 색상을 변경하려는 특정 단어
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
        private const val HANGUL_PATTERN = "^[a-zA-Z0-9._]*\$"
        val HANGUL_REGEX: Pattern = Pattern.compile(HANGUL_PATTERN)
    }
}

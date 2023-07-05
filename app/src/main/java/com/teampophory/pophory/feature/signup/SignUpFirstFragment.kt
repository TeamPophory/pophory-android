package com.teampophory.pophory.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentSignUpFirstBinding
import java.util.regex.Pattern

class SignUpFirstFragment : Fragment() {
    private val binding by viewBinding(FragmentSignUpFirstBinding::bind)
    private var buttonState: SignUpButtonInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSignUpFirstBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvErrorMessage.isVisible = false
        binding.btnDeleteEditText.isGone = true
        // edittext 상태
        setEditText()
        // edittext 삭제 버튼
        deleteAllEditText()
    }

    private fun deleteAllEditText() {
        binding.btnDeleteEditText.setOnClickListener {
            binding.editTvName.text.clear()
        }
    }

    private fun setEditText() {
        //텍스트창 활성화
        binding.editTvName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                // 포커스가 주어졌을 때
                if (hasFocus) {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_selected)
                } else {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_default)
                }
            }
            //edittext text 변화 감지
            doAfterTextChanged {
                binding.btnDeleteEditText.isGone = it?.isEmpty() == true
                // 글자 수 계산
                binding.tvTextCount.text = "(${it.toString().length}/6)"

                // 에러 메시지 : 현재 한국어만 지원함!
                val matcher = HANGUL_REGEX.matcher(binding.editTvName.text)
                if (!matcher.find()) {
                    binding.tvErrorMessage.text = "현재 한국어만 지원하고 있어요."
                    binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_error)
                    binding.tvErrorMessage.isVisible = true
                    buttonState?.onChangeState(false)
                } else if (it.toString().length < 2) {
                    binding.tvErrorMessage.text = "2-6글자 이내로 작성해주세요."
                    binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_error)
                    binding.tvErrorMessage.isVisible = true
                    buttonState?.onChangeState(false)
                } else {
                    binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_selected)
                    binding.tvErrorMessage.isVisible = false
                    buttonState?.onChangeState(true)
                }
            }
        }
    }

    fun setSignUpButtonInterface(buttonState: SignUpButtonInterface) {
        this.buttonState = buttonState
    }

    companion object {
        private const val HANGUL_PATTERN = "^[ㄱ-ㅎㅏ-ㅣ가-힣]*\$"
        val HANGUL_REGEX: Pattern = Pattern.compile(HANGUL_PATTERN)
    }
}

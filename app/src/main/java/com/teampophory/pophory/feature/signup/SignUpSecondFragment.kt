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
import com.teampophory.pophory.databinding.FragmentSignUpSecondBinding
import java.util.regex.Pattern

class SignUpSecondFragment : Fragment() {
    private val binding by viewBinding(FragmentSignUpSecondBinding::bind)
    private var buttonState: SignUpButtonInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSignUpSecondBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvErrorMessage.isVisible = false
        binding.btnDeleteEditText.isGone = true
        setEditText()
        deleteAllEditText()
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
            doAfterTextChanged {
                //X버튼 생성 여부
                binding.btnDeleteEditText.isGone = it?.isEmpty() == true
                //글자 수 계산
                binding.tvTextCount.text = "(${it.toString().length}/12)"

                val pattern = Pattern.compile(ID_PATTERN)
                val matcher = pattern.matcher(binding.editTvName.text)
                if (!matcher.find()) {
                    binding.tvErrorMessage.text = "*올바른 형식의 아이디가 아닙니다"
                    binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_error)
                    binding.tvErrorMessage.isVisible = true
                    buttonState?.onChangeState(false)
                } else if (it.toString().length < 4) {
                    binding.tvErrorMessage.text = "4-12글자 이내로 작성해주세요."
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
        const val ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[._])[a-zA-Z0-9._]*\$"
    }
}
package com.teampophory.pophory.feature.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.teampophory.pophory.R
import com.teampophory.pophory.databinding.FragmentSignUpSecondBinding
import java.util.regex.Pattern

class SignUpSecondFragment : Fragment() {

    private var _binding: FragmentSignUpSecondBinding? = null
    private var buttonState:SignUpButtonInterface? = null
    private val binding: FragmentSignUpSecondBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpSecondBinding.inflate(inflater, container, false)
        binding.tvErrorMessage.isVisible = false
        binding.btnDeleteEditText.isGone = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditText()

        deleteAllEditText()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun deleteAllEditText() {
        binding.btnDeleteEditText.setOnClickListener {
            binding.editTvName.text.clear()
        }
    }

    private fun setEditText() {
        //텍스트창 활성화
        binding.editTvName.apply {
            setOnFocusChangeListener { view, b ->
                //포커스가 주어졌을 때
                if (b) {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_selected)
                } else {
                    setBackgroundResource(R.drawable.bg_sign_up_edit_text_default)
                }
            }

            //edittext text 변화 감지
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //X버튼 생성 여부
                    binding.btnDeleteEditText.isGone = count < 1
                    //글자 수 계산
                    binding.tvTextCount.text = "(${s.toString().length}/12)"

                    val pattern = Pattern.compile(ID_PATTERN)
                    val matcher = pattern.matcher(binding.editTvName.text)
                    if (!matcher.find()) {
                        binding.tvErrorMessage.text = "*올바른 형식의 아이디가 아닙니다"
                        binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_error)
                        binding.tvErrorMessage.isVisible = true
                    } else if (s.toString().length < 4) {
                        binding.tvErrorMessage.text = "4-12글자 이내로 작성해주세요."
                        binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_error)
                        binding.tvErrorMessage.isVisible = true
                    } else {
                        binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_selected)
                        binding.tvErrorMessage.isVisible = false
                        buttonState?.setButtonState(true)
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }
    fun setSignUpButtonInterface(buttonState: SignUpButtonInterface){
        this.buttonState = buttonState
    }

    companion object {
        const val ID_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[._])[a-zA-Z0-9._]*\$"
    }
}
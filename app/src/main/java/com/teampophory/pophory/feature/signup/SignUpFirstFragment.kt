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
import com.teampophory.pophory.databinding.FragmentSignUpFirstBinding
import java.util.regex.Pattern

class SignUpFirstFragment : Fragment() {

    private var _binding: FragmentSignUpFirstBinding? = null
    private var buttonState:SignUpButtonInterface? = null
    private val binding: FragmentSignUpFirstBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpFirstBinding.inflate(inflater, container, false)
        binding.tvErrorMessage.isVisible = false
        binding.btnDeleteEditText.isGone = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //edittext 상태
        setEditText()
        //edittext 삭제 버튼
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
                    binding.tvTextCount.text = "(${s.toString().length}/6)"

                    //에러 메시지 : 현재 한국어만 지원함!
                    val pattern = Pattern.compile(HANGUL_PATTERN)
                    val matcher = pattern.matcher(binding.editTvName.text)
                    if (!matcher.find()) {
                        binding.tvErrorMessage.text = "현재 한국어만 지원하고 있어요."
                        binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_error)
                        binding.tvErrorMessage.isVisible = true
                        buttonState?.setButtonState(false)
                    } else if (s.toString().length < 2) {
                        binding.tvErrorMessage.text = "2-6글자 이내로 작성해주세요."
                        binding.editTvName.setBackgroundResource(R.drawable.bg_sign_up_edit_text_error)
                        binding.tvErrorMessage.isVisible = true
                        buttonState?.setButtonState(false)
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
        const val HANGUL_PATTERN =
            "^[ㄱ-ㅎㅏ-ㅣ가-힣]*\$"
    }
}

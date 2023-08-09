package com.teampophory.pophory.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.colorOf
import com.teampophory.pophory.common.primitive.textAppearance
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentSignUpThirdBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpThirdFragment : Fragment() {
    private val binding by viewBinding(FragmentSignUpThirdBinding::bind)
    private val signUpViewModel by activityViewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSignUpThirdBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //앨범 커버 이미지 초기화
        initAlbumCoverImage()
        //앨범 커버 선택
        selectAlbumCover()
        setSpannableString()
    }

    private fun initAlbumCoverImage() {
        //기본 앨범 커버 이미지
        binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_friends_1)

    }

    private fun setAlbumSelectState(number: Int){
        with(binding){
            ivAlbumSelect1.isVisible = number == 1
            ivAlbumSelect2.isVisible = number == 3
            ivAlbumSelect3.isVisible = number == 5
            ivAlbumSelect4.isVisible = number == 7
        }
        signUpViewModel.setAlbumCover(number)
    }

    private fun setSpannableString() {
        val fullText = getString(R.string.sign_up_third_title)
        val coloredText = "앨범 테마" // 색상을 변경하려는 특정 단어
        val splittedText = fullText.split(coloredText)

        val text = buildSpannedString {
            append(splittedText[0])
            color(colorOf(com.teampophory.pophory.designsystem.R.color.pophory_purple)) {
                textAppearance(requireContext(), com.teampophory.pophory.designsystem.R.style.TextAppearance_Pophory_HeadLineBold) {
                    append(coloredText)
                }
            }
            append(splittedText[1])
        }
        binding.tvTitle.text = text
    }

    private fun selectAlbumCover() {
        binding.ivAlbumSelect1.isVisible = true
        binding.ivAlbumSelect2.isVisible = false
        binding.ivAlbumSelect3.isVisible = false
        binding.ivAlbumSelect4.isVisible = false

        binding.ivAlbumCover1.setOnClickListener {
            setAlbumSelectState(1)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_friends_1)
        }
        binding.ivAlbumCover2.setOnClickListener {
            setAlbumSelectState(3)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_love_1)
        }
        binding.ivAlbumCover3.setOnClickListener {
            setAlbumSelectState(5)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_me_1)
        }
        binding.ivAlbumCover4.setOnClickListener {
            setAlbumSelectState(7)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_family_1)
        }
    }
}
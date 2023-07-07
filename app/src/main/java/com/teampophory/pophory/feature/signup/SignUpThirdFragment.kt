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
        setAlbumCoverImage()
        selectAlbumCover()
        setSpannableString()
    }

    private fun setAlbumCoverImage() {
        //기본 앨범 커버 이미지
        binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_friends)

    }

    private fun setAlbumSelectState(number: Int){
        with(binding){
            ivAlbumSelect1.isVisible = number == 1
            ivAlbumSelect2.isVisible = number == 2
            ivAlbumSelect3.isVisible = number == 3
            ivAlbumSelect4.isVisible = number == 4
        }
    }

    private fun setSpannableString() {
        val fullText = getString(R.string.sign_up_third_title)
        val coloredText = "앨범 커버" // 색상을 변경하려는 특정 단어
        val splittedText = fullText.split(coloredText)

        val text = buildSpannedString {
            append(splittedText[0])
            color(colorOf(R.color.pophory_purple)) {
                textAppearance(requireContext(), R.style.TextAppearance_Pophory_HeadLineBold) {
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
            signUpViewModel.setAlbumCover(1)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_friends)
        }
        binding.ivAlbumCover2.setOnClickListener {
            setAlbumSelectState(2)
            signUpViewModel.setAlbumCover(2)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_love)
        }
        binding.ivAlbumCover3.setOnClickListener {
            setAlbumSelectState(3)
            signUpViewModel.setAlbumCover(3)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_myalbum)
        }
        binding.ivAlbumCover4.setOnClickListener {
            setAlbumSelectState(4)
            signUpViewModel.setAlbumCover(4)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_collectbook)
        }
    }
}
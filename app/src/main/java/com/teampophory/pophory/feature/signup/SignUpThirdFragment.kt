package com.teampophory.pophory.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentSignUpThirdBinding

class SignUpThirdFragment : Fragment() {
    private val binding by viewBinding(FragmentSignUpThirdBinding::bind)
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
    }

    private fun setAlbumCoverImage() {
        //기본 앨범 커버 이미지
        binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_friends)

    }

    private fun setAlbumSelectState(number: Int){
        binding.ivAlbumSelect1.isVisible = number == 1
        binding.ivAlbumSelect2.isVisible = number == 2
        binding.ivAlbumSelect3.isVisible = number == 3
        binding.ivAlbumSelect4.isVisible = number == 4
    }

    private fun selectAlbumCover() {
        binding.ivAlbumSelect1.isVisible = true
        binding.ivAlbumSelect2.isVisible = false
        binding.ivAlbumSelect3.isVisible = false
        binding.ivAlbumSelect4.isVisible = false

        binding.ivAlbumCover1.setOnClickListener {
            setAlbumSelectState(1)
            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_friends)
        }
        binding.ivAlbumCover2.setOnClickListener {
            setAlbumSelectState(2)

            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_love)
        }
        binding.ivAlbumCover3.setOnClickListener {
            setAlbumSelectState(3)

            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_myalbum)
        }
        binding.ivAlbumCover4.setOnClickListener {
            setAlbumSelectState(4)

            binding.ivAlbumCover.setImageResource(R.drawable.ic_album_cover_collectbook)
        }
    }
}
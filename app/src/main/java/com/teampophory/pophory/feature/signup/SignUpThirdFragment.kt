package com.teampophory.pophory.feature.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
        selectAlbumCover()
    }

    private fun selectAlbumCover() {
        binding.ivAlbumSelect1.isVisible = true
        binding.ivAlbumSelect2.isVisible = false
        binding.ivAlbumSelect3.isVisible = false
        binding.ivAlbumSelect4.isVisible = false
        binding.ivAlbumCover1.setOnClickListener {
            binding.ivAlbumSelect1.isVisible = true
            binding.ivAlbumSelect2.isVisible = false
            binding.ivAlbumSelect3.isVisible = false
            binding.ivAlbumSelect4.isVisible = false
        }
        binding.ivAlbumCover2.setOnClickListener {
            binding.ivAlbumSelect1.isVisible = false
            binding.ivAlbumSelect2.isVisible = true
            binding.ivAlbumSelect3.isVisible = false
            binding.ivAlbumSelect4.isVisible = false
        }
        binding.ivAlbumCover3.setOnClickListener {
            binding.ivAlbumSelect1.isVisible = false
            binding.ivAlbumSelect2.isVisible = false
            binding.ivAlbumSelect3.isVisible = true
            binding.ivAlbumSelect4.isVisible = false
        }
        binding.ivAlbumCover4.setOnClickListener {
            binding.ivAlbumSelect1.isVisible = false
            binding.ivAlbumSelect2.isVisible = false
            binding.ivAlbumSelect3.isVisible = false
            binding.ivAlbumSelect4.isVisible = true
        }
    }
}
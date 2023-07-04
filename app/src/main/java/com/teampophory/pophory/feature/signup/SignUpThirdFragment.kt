package com.teampophory.pophory.feature.signup

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.teampophory.pophory.databinding.FragmentSignUpThirdBinding

class SignUpThirdFragment : Fragment() {

    private var _binding: FragmentSignUpThirdBinding? = null
    private val binding: FragmentSignUpThirdBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpThirdBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectAlbumCover()
    }

    override fun onDestroyView() {
        super.onDestroy()
        _binding = null
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
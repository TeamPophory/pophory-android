package com.teampophory.pophory.albumsort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teampophory.pophory.R
import com.teampophory.pophory.databinding.BottomSheetAlbumSortBinding
import com.teampophory.pophory.feature.album.list.AlbumListViewModel

class AlbumSortBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAlbumSortBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AlbumListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAlbumSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListMenuViews()
    }

    private fun initListMenuViews() {
        val drawable = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_check_big)
        with(binding) {
            tvSortNewest.setOnClickListener {
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                tvSortNewest.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                tvSortOldest.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
                viewModel.sortPhotoList(AlbumSortType.NEWEST)
                dismissAllowingStateLoss()
            }
            tvSortOldest.setOnClickListener {
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                tvSortNewest.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
                tvSortOldest.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                viewModel.sortPhotoList(AlbumSortType.OLDEST)
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        val TAG: String = AlbumSortBottomSheet::class.java.simpleName
    }
}
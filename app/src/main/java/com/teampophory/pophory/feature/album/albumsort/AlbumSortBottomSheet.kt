package com.teampophory.pophory.feature.album.albumsort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teampophory.pophory.databinding.BottomSheetAlbumSortBinding
import com.teampophory.pophory.feature.album.list.AlbumListViewModel

class AlbumSortBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAlbumSortBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AlbumListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = BottomSheetAlbumSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCheckButtonState(viewModel.albumSortType.value)
        initListMenuViews()
    }

    private fun initListMenuViews() {
        with(binding) {
            tvSortNewest.setOnClickListener {
                viewModel.sortPhotoList(AlbumSortType.NEWEST)
                dismissAllowingStateLoss()
            }
            tvSortOldest.setOnClickListener {
                viewModel.sortPhotoList(AlbumSortType.OLDEST)
                dismissAllowingStateLoss()
            }
        }
    }

    private fun setCheckButtonState(sortType: AlbumSortType) {
        binding.ivCheckNewest.isVisible = sortType == AlbumSortType.NEWEST
        binding.ivCheckOldest.isVisible = sortType == AlbumSortType.OLDEST
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        val TAG: String = AlbumSortBottomSheet::class.java.simpleName
    }
}

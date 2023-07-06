package com.teampophory.pophory.albumsort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teampophory.pophory.databinding.ModalBottomSheetContentBinding
import com.teampophory.pophory.feature.album.list.AlbumListViewModel

class AlbumSortBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetContentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<AlbumListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ModalBottomSheetContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
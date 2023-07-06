package com.teampophory.pophory.feature.home.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teampophory.pophory.common.fragment.viewLifeCycle
import com.teampophory.pophory.common.fragment.viewLifeCycleScope
import com.teampophory.pophory.common.view.GridSpacingItemDecoration
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStudioSelectBinding
import com.teampophory.pophory.feature.home.photo.adapter.StudioAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class StudioSelectFragment : BottomSheetDialogFragment() {
    private val binding by viewBinding(FragmentStudioSelectBinding::bind)
    private val viewModel by activityViewModels<AddPhotoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentStudioSelectBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StudioAdapter(requireContext()) {
            viewModel.onUpdateStudio(it)
            dismissAllowingStateLoss()
        }
        binding.listStudio.adapter = adapter
        binding.listStudio.addItemDecoration(
            GridSpacingItemDecoration(3, 12.dp, false)
        )

        viewModel.studio
            .flowWithLifecycle(viewLifeCycle)
            .onEach {
                adapter.submitList(it)
            }.launchIn(viewLifeCycleScope)
    }
}

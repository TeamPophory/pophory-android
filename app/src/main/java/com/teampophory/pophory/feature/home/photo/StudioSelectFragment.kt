package com.teampophory.pophory.feature.home.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStudioSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudioSelectFragment : BottomSheetDialogFragment() {
    private val binidng by viewBinding(FragmentStudioSelectBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentStudioSelectBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

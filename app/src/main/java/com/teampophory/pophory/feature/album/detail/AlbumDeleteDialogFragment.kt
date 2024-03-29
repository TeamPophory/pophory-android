package com.teampophory.pophory.feature.album.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.teampophory.pophory.R
import com.teampophory.pophory.common.context.dialogWidthPercent
import com.teampophory.pophory.common.view.setOnSingleClickListener
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentAlbumDeleteDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDeleteDialogFragment : DialogFragment() {

    private val viewModel by activityViewModels<AlbumDetailViewModel>()
    private val binding: FragmentAlbumDeleteDialogBinding by viewBinding(
        FragmentAlbumDeleteDialogBinding::bind,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_album_delete_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtonClickListener()
    }

    override fun onResume() {
        super.onResume()
        context?.dialogWidthPercent(dialog)
        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun initButtonClickListener() {
        with(binding) {
            tvReturnButton.setOnClickListener {
                dismissAllowingStateLoss()
            }
            tvDeleteButton.setOnSingleClickListener {
                viewModel.deleteAlbum()
            }
        }
    }

    companion object {
        val TAG: String = AlbumDeleteDialogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = AlbumDeleteDialogFragment()
    }
}

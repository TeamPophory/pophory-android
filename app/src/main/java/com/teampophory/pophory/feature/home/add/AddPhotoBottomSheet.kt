package com.teampophory.pophory.feature.home.add

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teampophory.pophory.databinding.BottomSheetHomeAddPhotoBinding
import com.teampophory.pophory.feature.home.HomeViewModel
import com.teampophory.pophory.feature.home.photo.AddPhotoActivity


class AddPhotoBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetHomeAddPhotoBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<HomeViewModel>()
    private lateinit var imagePicker: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var addPhotoResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetHomeAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            val currentAlbumPosition = viewModel.homeState.value.currentAlbumPosition
            val albumItem = viewModel.homeState.value.currentAlbums?.getOrNull(currentAlbumPosition)
            if (uri != null && albumItem != null) {
                val intent = AddPhotoActivity.getIntent(context, uri.toString(), albumItem)
                addPhotoResultLauncher.launch(intent)
            }
        }

        addPhotoResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    viewModel.eventAlbumCountUpdate()
                    dismiss()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListMenuViews()
    }

    private fun initListMenuViews() {
        with(binding) {
            layoutQr.setOnClickListener {

            }
            layoutGallery.setOnClickListener {
                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        val TAG: String = AddPhotoBottomSheet::class.java.simpleName
    }
}
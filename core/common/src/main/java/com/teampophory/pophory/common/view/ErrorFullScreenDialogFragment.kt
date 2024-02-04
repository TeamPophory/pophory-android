package com.teampophory.pophory.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teampophory.pophory.common.R

class ErrorFullScreenDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_error_fullscreen, container, false)
    }

    companion object {
        const val TAG = "ErrorFullScreenDialogFragment"

        @JvmStatic
        fun newInstance() = ErrorFullScreenDialogFragment()
    }
}

package com.teampophory.pophory.common.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teampophory.pophory.common.R

class LoadingProgressIndicator : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        context?.let {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    companion object {
        const val TAG = "LoadingProgressIndicator"

        @JvmStatic
        fun newInstance() = LoadingProgressIndicator()
    }
}

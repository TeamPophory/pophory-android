package com.teampophory.pophory.feature.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {
    private val binding by viewBinding(FragmentStoreBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

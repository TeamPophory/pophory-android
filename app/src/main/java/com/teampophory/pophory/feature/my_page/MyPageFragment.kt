package com.teampophory.pophory.feature.my_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private val binding by viewBinding(FragmentMyPageBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

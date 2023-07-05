package com.teampophory.pophory.feature.home.store

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.teampophory.pophory.R
import com.teampophory.pophory.common.fragment.colorOf
import com.teampophory.pophory.common.primitive.font
import com.teampophory.pophory.common.primitive.textAppearance
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.FragmentStoreBinding
import com.teampophory.pophory.feature.album.AlbumListActivity
import com.teampophory.pophory.feature.home.store.apdater.StoreAdapter

class StoreFragment : Fragment() {
    private val binding by viewBinding(FragmentStoreBinding::bind)

    private var adapter: StoreAdapter? = null
    private val viewModel by viewModels<StoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        observeAlbumList()
        setSpannableString()
    }

    private fun setupViewPager() {
        //2차 스프린트를 위해 position 값을 받아둠
        adapter = StoreAdapter(ItemDiffCallback(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new }
        )) { position ->
            val intent = Intent(context, AlbumListActivity::class.java)
            intent.putExtra("itemId", position)
            requireContext().startActivity(intent)
        }

        binding.viewpagerStore.adapter = adapter

        //1차 스프린트용 입력 방지
        binding.viewpagerStore.isUserInputEnabled = false
    }

    private fun observeAlbumList() {
        viewModel.albumList.observe(viewLifecycleOwner) { list ->
            adapter?.submitList(list)
        }
    }

    private fun setSpannableString() {
        val fullText = getString(R.string.store_welcome)
        val coloredText = "포포리 앨범" // 색상을 변경하려는 특정 단어
        val splittedText = fullText.split(coloredText)

        val text = buildSpannedString {
            color(colorOf(R.color.pophory_purple)) {
                textAppearance(requireContext(), R.style.TextAppearance_Pophory_HeadLineBold) {
                    append("포포리 앨범")
                }
            }
            append(splittedText[1])
        }

        binding.tvStoreWelcome.text = text
    }
}

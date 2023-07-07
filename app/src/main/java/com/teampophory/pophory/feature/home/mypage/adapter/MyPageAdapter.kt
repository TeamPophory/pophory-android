package com.teampophory.pophory.feature.home.mypage.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teampophory.pophory.R
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemMypageEmptyBinding
import com.teampophory.pophory.databinding.ItemMypageFeedBinding
import com.teampophory.pophory.databinding.ItemMypageProfileBinding
import com.teampophory.pophory.feature.home.mypage.MyPageDisplayItem

class MyPageAdapter(
    private val onItemClicked: (MyPageDisplayItem.Photo) -> Unit
) : ListAdapter<MyPageDisplayItem, RecyclerView.ViewHolder>(
        ItemDiffCallback<MyPageDisplayItem>(
            onItemsTheSame = { old, new -> old == new },
            onContentsTheSame = { old, new -> old == new }
        )
) {
    companion object {
        const val VIEW_TYPE_PROFILE = 0
        const val VIEW_TYPE_PHOTO = 1
        const val VIEW_TYPE_EMPTY = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MyPageDisplayItem.Profile -> VIEW_TYPE_PROFILE
            is MyPageDisplayItem.Photo -> VIEW_TYPE_PHOTO
            is MyPageDisplayItem.Empty -> VIEW_TYPE_EMPTY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            VIEW_TYPE_PROFILE -> {
                val binding =
                    ItemMypageProfileBinding.inflate(LayoutInflater.from(context), parent, false)
                ProfileViewHolder(binding, context)
            }

            VIEW_TYPE_PHOTO -> {
                val binding =
                    ItemMypageFeedBinding.inflate(LayoutInflater.from(context), parent, false)
                PhotoViewHolder(binding, onItemClicked)
            }

            VIEW_TYPE_EMPTY -> {
                val binding =
                    ItemMypageEmptyBinding.inflate(LayoutInflater.from(context), parent, false)
                EmptyViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is MyPageDisplayItem.Profile -> {
                (holder as ProfileViewHolder).bind(item)
            }

            is MyPageDisplayItem.Photo -> {
                (holder as PhotoViewHolder).bind(item)
            }

            else -> {}
        }
    }

    class PhotoViewHolder(
        private val binding: ItemMypageFeedBinding,
        private val onItemClicked: (MyPageDisplayItem.Photo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: MyPageDisplayItem.Photo) {
            binding.ivMypageFeedItem.load(photo.photo.photoUrl)
            itemView.setOnClickListener {
                onItemClicked(photo)
            }
        }
    }

    class ProfileViewHolder(
        private val binding: ItemMypageProfileBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profileInfo: MyPageDisplayItem.Profile) {
            with(binding) {
                tvMypageName.text = profileInfo.realName
                tvMypagePictureCount.text = setSpannableString(profileInfo.photoCount)
            }
        }

        private fun setSpannableString(myPageInfoDataPhotoCount: Int): SpannableStringBuilder {
            val fullText =
                context.getString(R.string.mypage_picture_count, myPageInfoDataPhotoCount)
            val coloredText = myPageInfoDataPhotoCount.toString()

            val spannableStringBuilder = SpannableStringBuilder(fullText)
            val start = fullText.indexOf(coloredText)
            val end = start + coloredText.length

            if (start != -1) {
                spannableStringBuilder.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            context,
                            R.color.pophory_purple
                        )
                    ),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return spannableStringBuilder
        }
    }

    class EmptyViewHolder(private val binding: ItemMypageEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}


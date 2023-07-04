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
import com.teampophory.pophory.databinding.ItemMypageFeedBinding
import com.teampophory.pophory.databinding.ItemMypageProfileBinding
import com.teampophory.pophory.feature.home.mypage.model.MyPageInfo


class MyPageAdapter(
    private val onItemClicked: (MyPageInfo.Photo) -> Unit
) : ListAdapter<Any, RecyclerView.ViewHolder>(
    ItemDiffCallback<Any>(
        onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
        onContentsTheSame = { old, new -> old == new }
    )
) {
    companion object {
        const val VIEW_TYPE_PROFILE = 0
        const val VIEW_TYPE_PHOTO = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_PROFILE
            else -> VIEW_TYPE_PHOTO
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

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> {
                val profileInfo = getItem(0) as MyPageInfo
                holder.bind(profileInfo)
            }

            is PhotoViewHolder -> {
                val photo = getItem(position) as MyPageInfo.Photo
                holder.bind(photo)
            }

            else -> throw IllegalArgumentException("Invalid view holder")
        }
    }

    class PhotoViewHolder(
        private val binding: ItemMypageFeedBinding,
        private val onItemClicked: (MyPageInfo.Photo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: MyPageInfo.Photo) {
            binding.ivMypageFeedItem.load(photo.photoUrl)
            itemView.setOnClickListener {
                onItemClicked(photo)
            }
        }
    }

    class ProfileViewHolder(
        private val binding: ItemMypageProfileBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profileInfo: MyPageInfo) {
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
}


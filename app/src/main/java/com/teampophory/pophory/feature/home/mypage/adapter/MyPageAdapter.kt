package com.teampophory.pophory.feature.home.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemMypageFeedBinding
import com.teampophory.pophory.feature.home.mypage.model.MyPageInfo

class MyPageAdapter(
    private val onItemClicked: (Int) -> Unit
) : ListAdapter<MyPageInfo.Photo, MyPageAdapter.MyPageViewHolder>(
    ItemDiffCallback<MyPageInfo.Photo>(
        onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
        onContentsTheSame = { old, new -> old == new }
    )
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageViewHolder {
        val binding =
            ItemMypageFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPageViewHolder(binding, onItemClicked)
    }

    class MyPageViewHolder(
        private val binding: ItemMypageFeedBinding,
        private val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: MyPageInfo.Photo) {
            binding.ivMypageFeedItem.load(photo.photoUrl)  // Assuming 'photoUrl' is a property of MyPageInfo.Photo.
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: MyPageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

package com.teampophory.pophory.feature.on_boarding.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemOnboardingViewpagerBinding
import com.teampophory.pophory.feature.on_boarding.OnBoardingData

class OnBoardingViewPagerAdapter :
    ListAdapter<OnBoardingData, OnBoardingViewPagerAdapter.ViewHolder>(
        DIFF_UTIL
    ) {
    class ViewHolder(private val binding: ItemOnboardingViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnBoardingData) {
            binding.iv.background = item.background
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOnboardingViewpagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DIFF_UTIL =
            ItemDiffCallback<OnBoardingData>(
                onItemsTheSame = { old, new -> old.image == new.image },
                onContentsTheSame = { old, new -> old == new })
    }

}

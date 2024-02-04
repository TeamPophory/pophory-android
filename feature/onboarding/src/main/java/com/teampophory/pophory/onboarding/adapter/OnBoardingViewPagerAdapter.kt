package com.teampophory.pophory.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.onboarding.databinding.ItemOnboardingViewpagerBinding
import com.teampophory.pophory.onboarding.model.OnBoardingData

class OnBoardingViewPagerAdapter :
    ListAdapter<OnBoardingData, OnBoardingViewPagerAdapter.ViewHolder>(
        DIFF_UTIL,
    ) {
    class ViewHolder(private val binding: ItemOnboardingViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OnBoardingData) {
            binding.iv.setBackgroundResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOnboardingViewpagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DIFF_UTIL =
            ItemDiffCallback<OnBoardingData>(
                onItemsTheSame = { old, new -> old.image == new.image },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}

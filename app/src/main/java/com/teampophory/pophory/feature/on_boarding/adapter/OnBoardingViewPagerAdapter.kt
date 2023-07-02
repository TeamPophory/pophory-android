package com.teampophory.pophory.feature.on_boarding.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampophory.pophory.databinding.ItemOnboardingViewpagerBinding
import com.teampophory.pophory.feature.on_boarding.OnBoardingData

class OnBoardingViewPagerAdapter : ListAdapter<OnBoardingData, OnBoardingViewPagerAdapter.ViewHolder>(
    diffUtil
) {

    class ViewHolder(private val binding : ItemOnboardingViewpagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : OnBoardingData) {
            binding.iv.background = Color.parseColor(item.image).toDrawable()
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
        val diffUtil = object : DiffUtil.ItemCallback<OnBoardingData>() {

            // 두 아이템이 동일한 아이템인지 체크. 보통 고유한 id를 기준으로 비교
            override fun areItemsTheSame(oldItem: OnBoardingData, newItem: OnBoardingData): Boolean {
                return oldItem == newItem
            }

            // 두 아이템이 동일한 내용을 가지고 있는지 체크. areItemsTheSame()이 true일때 호출됨
            override fun areContentsTheSame(oldItem: OnBoardingData, newItem: OnBoardingData): Boolean {
                return oldItem.image == newItem.image
            }
        }
    }

}

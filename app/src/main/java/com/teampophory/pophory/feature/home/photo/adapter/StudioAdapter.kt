package com.teampophory.pophory.feature.home.photo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teampophory.pophory.common.view.ItemDiffCallback
import com.teampophory.pophory.databinding.ItemStudioSelectBinding
import com.teampophory.pophory.feature.home.photo.model.StudioUiModel

class StudioAdapter(
    context: Context,
    private val onUpdate: (StudioUiModel) -> Unit
) : ListAdapter<StudioUiModel, StudioAdapter.ViewHolder>(
    ItemDiffCallback(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id }
    )
) {
    private val inflater = LayoutInflater.from(context)

    class ViewHolder(
        private val binding: ItemStudioSelectBinding,
        private val onUpdate: (StudioUiModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudioUiModel) {
            binding.txtStudio.text = item.name
            binding.txtStudio.isSelected = item.isSelected
            binding.root.setOnClickListener {
                onUpdate(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStudioSelectBinding.inflate(inflater, parent, false),
            onUpdate
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

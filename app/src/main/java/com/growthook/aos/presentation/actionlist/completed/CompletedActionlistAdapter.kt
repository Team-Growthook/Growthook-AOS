package com.growthook.aos.presentation.actionlist.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemCompletedActionplanBinding
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.util.extension.ItemDiffCallback

class CompletedActionlistAdapter :
    ListAdapter<Actionplan, CompletedActionlistAdapter.CompletedActionlistViewHolder>(
        ItemDiffCallback<Actionplan>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id },
        ),
    ) {
    class CompletedActionlistViewHolder(private val binding: ItemCompletedActionplanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Actionplan) {
            binding.tvCompletedActionplanTitle.text = data.content
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompletedActionlistViewHolder {
        val binding =
            ItemCompletedActionplanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return CompletedActionlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedActionlistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Actionplan,
        )
    }
}

package com.growthook.aos.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemHomeInsightLockBinding
import com.growthook.aos.databinding.ItemHomeInsightNoActionBinding
import com.growthook.aos.databinding.ItemHomeInsightYesActionBinding
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.util.ItemDiffCallback

class HomeInsightAdapter : ListAdapter<Insight, RecyclerView.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        when {
            currentList[position].isLocked -> return LOCK
            currentList[position].isAction -> return YES_ACTION
        }

        return NO_ACTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            LOCK -> {
                val binding: ItemHomeInsightLockBinding = ItemHomeInsightLockBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
                return LockedInsightViewHolder(binding)
            }

            YES_ACTION -> {
                val binding: ItemHomeInsightYesActionBinding =
                    ItemHomeInsightYesActionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                return YesActionViewHolder(binding)
            }
        }

        val binding: ItemHomeInsightNoActionBinding = ItemHomeInsightNoActionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return NoActionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LockedInsightViewHolder -> holder.onBind(currentList[position])
            is YesActionViewHolder -> holder.onBind(currentList[position])
            is NoActionViewHolder -> holder.onBind(currentList[position])
        }
    }

    class LockedInsightViewHolder(private val binding: ItemHomeInsightLockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight) {
            if (item.isLocked) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_insight_no_action_yes_scrap)
            }
            binding.tvHomeInsightTitle.text = item.title
            binding.tvHomeInsightLock.text = "${item.remainedLock}일 후 잠금"
        }
    }

    class YesActionViewHolder(private val binding: ItemHomeInsightYesActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight) {
            if (item.isLocked) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_insight_yes_action_yes_scrap)
            }
            binding.tvHomeInsightTitle.text = item.title
            binding.tvHomeInsightLock.text = "${item.remainedLock}일 후 잠금"
        }
    }

    class NoActionViewHolder(private val binding: ItemHomeInsightNoActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight) {
            if (item.isLocked) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_insight_no_action_yes_scrap)
            }
            binding.tvHomeInsightTitle.text = item.title
            binding.tvHomeInsightLock.text = "${item.remainedLock}일 후 잠금"
        }
    }

    companion object {

        private const val LOCK = 0
        private const val NO_ACTION = 1
        private const val YES_ACTION = 2

        private val diffCallback =
            ItemDiffCallback<Insight>(
                onContentsTheSame = { old, new -> old == new },
                onItemsTheSame = { old, new -> old == new },
            )
    }
}

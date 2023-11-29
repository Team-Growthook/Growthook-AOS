package com.growthook.aos.presentation.home

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemHomeInsightLockBinding
import com.growthook.aos.databinding.ItemHomeInsightNoActionBinding
import com.growthook.aos.databinding.ItemHomeInsightYesActionBinding
import com.growthook.aos.domain.entity.Insight
import com.growthook.aos.util.extension.ItemDiffCallback
import timber.log.Timber
import javax.annotation.Nullable

class HomeInsightAdapter() :
    ListAdapter<Insight, RecyclerView.ViewHolder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    private lateinit var selectionTracker: SelectionTracker<Long>
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setSelectionTracker(selectionTracker: SelectionTracker<Long>) {
        this.selectionTracker = selectionTracker
    }

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
            is LockedInsightViewHolder -> {
                holder.onBind(currentList[position], position)
            }

            is YesActionViewHolder -> holder.onBind(currentList[position], position)
            is NoActionViewHolder -> holder.onBind(currentList[position], position)
        }
    }

    inner class LockedInsightViewHolder(private val binding: ItemHomeInsightLockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight, itemPosition: Int) {
            if (item.isScraped) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_insight_no_action_yes_scrap)
            }
            binding.tvHomeInsightTitle.text = item.title
            binding.tvHomeInsightLock.text = "${item.remainedLock}일 후 잠금"
            binding.root.setOnClickListener {
                selectionTracker.select(itemPosition.toLong())
                Timber.d("선택된 아이템 ${selectionTracker.selection}")
            }
        }

        fun getItemDetails(viewHolder: RecyclerView.ViewHolder?): ItemDetailsLookup.ItemDetails<Long> {
            return object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getSelectionKey(): Long? {
                    return itemId
                }

                override fun getPosition(): Int {
                    if (viewHolder == null) {
                        return RecyclerView.NO_POSITION
                    }
                    return viewHolder.absoluteAdapterPosition
                }
            }
        }
    }

    inner class YesActionViewHolder(private val binding: ItemHomeInsightYesActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight, itemPosition: Int) {
            if (item.isScraped) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_insight_yes_action_yes_scrap)
            }
            binding.tvHomeInsightTitle.text = item.title
            binding.tvHomeInsightLock.text = "${item.remainedLock}일 후 잠금"
            binding.root.setOnLongClickListener {
                selectionTracker.select(itemPosition.toLong())
                binding.viewHomeInsightClick.visibility = View.VISIBLE
                true
            }
            binding.root.setOnClickListener {
                selectionTracker.select(itemPosition.toLong())
                Timber.d("선택된 아이템 ${selectionTracker.selection}")
            }
            if (!selectionTracker.isSelected(itemPosition.toLong())) {
                binding.viewHomeInsightClick.visibility = View.INVISIBLE
            }
        }

        fun getItemDetails(viewHolder: RecyclerView.ViewHolder?): ItemDetailsLookup.ItemDetails<Long> {
            return object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getSelectionKey(): Long? {
                    return itemId
                }

                override fun getPosition(): Int {
                    if (viewHolder == null) {
                        return RecyclerView.NO_POSITION
                    }
                    return viewHolder.absoluteAdapterPosition
                }
            }
        }
    }

    inner class NoActionViewHolder(private val binding: ItemHomeInsightNoActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight, itemPosition: Int) {
            if (item.isScraped) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_insight_no_action_yes_scrap)
            }
            binding.tvHomeInsightTitle.text = item.title
            binding.tvHomeInsightLock.text = "${item.remainedLock}일 후 잠금"
            binding.root.setOnLongClickListener {
                binding.viewHomeInsightClick.visibility = View.VISIBLE
                selectionTracker.select(itemPosition.toLong())
                binding.viewHomeInsightClick.visibility = View.VISIBLE
                Timber.d("선택된 아이템 ${selectionTracker.selection}")
                true
            }
            binding.root.setOnClickListener {
                selectionTracker.select(itemPosition.toLong())
            }
            if (!selectionTracker.isSelected(itemPosition.toLong())) {
                binding.viewHomeInsightClick.visibility = View.INVISIBLE
            }
        }

        fun getItemDetails(viewHolder: RecyclerView.ViewHolder?): ItemDetailsLookup.ItemDetails<Long> {
            return object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getSelectionKey(): Long? {
                    return itemId
                }

                override fun getPosition(): Int {
                    if (viewHolder == null) {
                        return RecyclerView.NO_POSITION
                    }
                    return viewHolder.absoluteAdapterPosition
                }
            }
        }
    }

    class InsightDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
        @Nullable
        override fun getItemDetails(@NonNull motionEvent: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)

            if (view != null) {
                return when (val viewHolder = recyclerView.getChildViewHolder(view)) {
                    is LockedInsightViewHolder -> viewHolder.getItemDetails(viewHolder)
                    is YesActionViewHolder -> viewHolder.getItemDetails(viewHolder)
                    is NoActionViewHolder -> viewHolder.getItemDetails(viewHolder)
                    else -> null
                }
            }
            return null
        }
    }

    fun getSelectedInsight(): Insight? {
        val selectedItemId = selectionTracker.selection.firstOrNull()
        return if (selectedItemId == null) {
            null
        } else {
            currentList[selectedItemId.toInt()]
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

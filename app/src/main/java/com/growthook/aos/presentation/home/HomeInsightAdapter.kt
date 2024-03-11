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
import com.growthook.aos.util.extension.setOnSingleClickListener
import timber.log.Timber
import javax.annotation.Nullable

class HomeInsightAdapter(
    private val selectedItem: (Insight) -> Unit,
    private val clickScrap: (Insight) -> Unit,
) :
    ListAdapter<Insight, RecyclerView.ViewHolder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    private lateinit var selectionLongTracker: SelectionTracker<Long>

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun setSelectionLongTracker(selectionTracker: SelectionTracker<Long>) {
        this.selectionLongTracker = selectionTracker
    }

    override fun getItemViewType(position: Int): Int {
        when {
            currentList[position].isLocked -> return LOCK
            currentList[position].remainingDays <= 0 -> return ALREADY_LOCKED
        }

        return NOT_YET_LOCKED
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

            ALREADY_LOCKED -> {
                val binding: ItemHomeInsightYesActionBinding =
                    ItemHomeInsightYesActionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    )
                return AlreadyLockedViewHolder(binding)
            }
        }

        val binding: ItemHomeInsightNoActionBinding = ItemHomeInsightNoActionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return NotYetLockedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LockedInsightViewHolder -> {
                holder.onBind(currentList[position], position)
            }

            is AlreadyLockedViewHolder -> holder.onBind(currentList[position], position)
            is NotYetLockedViewHolder -> holder.onBind(currentList[position], position)
        }
    }

    inner class LockedInsightViewHolder(private val binding: ItemHomeInsightLockBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight, itemPosition: Int) {
            if (item.isScraped) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_scrap_unselected)
            } else {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_scrap_unselected)
            }
            binding.tvHomeInsightTitle.text = item.name
            binding.tvHomeInsightLock.text = "잠금"
            binding.root.setOnSingleClickListener {
                selectedItem(item)
            }
        }
    }

    inner class AlreadyLockedViewHolder(private val binding: ItemHomeInsightYesActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight, itemPosition: Int) {
            if (item.isScraped) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_scrap_yes_action_selected)
            } else {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_scrap_unselected)
            }

            binding.tvHomeInsightLock.text = "잠금 해제 완료!"

            binding.tvHomeInsightTitle.text = item.name
            binding.root.setOnLongClickListener {
                selectionLongTracker.select(itemPosition.toLong())
                binding.viewHomeInsightClick.visibility = View.VISIBLE
                true
            }
            binding.root.setOnSingleClickListener {
                selectedItem(item)
            }
            if (!selectionLongTracker.isSelected(itemPosition.toLong())) {
                binding.viewHomeInsightClick.visibility = View.INVISIBLE
            } else {
                binding.viewHomeInsightClick.visibility = View.VISIBLE
            }
            binding.btnHomeScrap.setOnClickListener {
                clickScrap(item)
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

    inner class NotYetLockedViewHolder(private val binding: ItemHomeInsightNoActionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Insight, itemPosition: Int) {
            if (item.isScraped) {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_scrap_selected)
            } else {
                binding.btnHomeScrap.setImageResource(R.drawable.ic_scrap_unselected)
            }

            binding.tvHomeInsightLock.text = "${item.remainingDays}일 후 잠금"

            binding.tvHomeInsightTitle.text = item.name
            binding.root.setOnLongClickListener {
                binding.viewHomeInsightClick.visibility = View.VISIBLE
                selectionLongTracker.select(itemPosition.toLong())
                Timber.d("선택된 아이템 ${selectionLongTracker.selection}")
                true
            }
            binding.root.setOnSingleClickListener {
                selectedItem(item)
            }
            if (!selectionLongTracker.isSelected(itemPosition.toLong())) {
                binding.viewHomeInsightClick.visibility = View.INVISIBLE
            } else {
                binding.viewHomeInsightClick.visibility = View.VISIBLE
            }
            binding.btnHomeScrap.setOnClickListener {
                clickScrap(item)
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
                    is AlreadyLockedViewHolder -> viewHolder.getItemDetails(viewHolder)
                    is NotYetLockedViewHolder -> viewHolder.getItemDetails(viewHolder)
                    else -> null
                }
            }
            return null
        }
    }

    fun getSelectedLongInsight(): Insight? {
        val selectedItemId = selectionLongTracker.selection.firstOrNull()
        return if (selectedItemId == null) {
            null
        } else {
            currentList[selectedItemId.toInt()]
        }
    }

    companion object {

        private const val LOCK = 0
        private const val NOT_YET_LOCKED = 1
        private const val ALREADY_LOCKED = 2

        private val diffCallback =
            ItemDiffCallback<Insight>(
                onContentsTheSame = { old, new -> old == new },
                onItemsTheSame = { old, new -> old == new },
            )
    }
}

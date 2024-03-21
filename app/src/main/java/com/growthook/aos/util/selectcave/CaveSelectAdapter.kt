package com.growthook.aos.util.selectcave

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemHomeSelectCaveBinding
import com.growthook.aos.domain.entity.Cave
import com.growthook.aos.util.extension.ItemDiffCallback
import javax.annotation.Nullable

class CaveSelectAdapter() :
    ListAdapter<Cave, CaveSelectAdapter.CaveSelectViewHolder>(diffCallback) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaveSelectViewHolder {
        val binding: ItemHomeSelectCaveBinding =
            ItemHomeSelectCaveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CaveSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CaveSelectViewHolder, position: Int) {
        return holder.onBind(currentList[position], position)
    }

    inner class CaveSelectViewHolder(private val binding: ItemHomeSelectCaveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Cave, itemPosition: Int) {
            binding.tvSelectCaveTitle.text = item.name

            binding.root.setOnClickListener {
                binding.root.setBackgroundResource(R.color.Gray500)
                binding.ivSelectCaveIsSelected.visibility = View.VISIBLE
                binding.tvSelectCaveTitle.setTypeface(null, Typeface.BOLD)

                selectionTracker.select(itemPosition.toLong())
            }

            if (!selectionTracker.isSelected(itemPosition.toLong())) {
                binding.root.setBackgroundResource(R.color.Gray400)
                binding.tvSelectCaveTitle.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.Gray200,
                    ),
                )
                binding.ivSelectCaveIsSelected.visibility = View.INVISIBLE
                binding.tvSelectCaveTitle.setTypeface(null, Typeface.NORMAL)
            } else {
                binding.root.setBackgroundResource(R.color.Gray500)
                binding.ivSelectCaveIsSelected.visibility = View.VISIBLE
                binding.tvSelectCaveTitle.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.MainGreen400,
                    ),
                )
                binding.tvSelectCaveTitle.setTypeface(null, Typeface.BOLD)
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
                    is CaveSelectViewHolder -> viewHolder.getItemDetails(
                        viewHolder,
                    )

                    else -> null
                }
            }
            return null
        }
    }

    fun getSelectedCave(): Cave? {
        val selectedItemId = selectionTracker.selection.firstOrNull()
        return if (selectedItemId == null) {
            null
        } else {
            currentList[selectedItemId.toInt()]
        }
    }

    companion object {
        private val diffCallback =
            ItemDiffCallback<Cave>(
                onContentsTheSame = { old, new -> old == new },
                onItemsTheSame = { old, new -> old == new },
            )
    }
}

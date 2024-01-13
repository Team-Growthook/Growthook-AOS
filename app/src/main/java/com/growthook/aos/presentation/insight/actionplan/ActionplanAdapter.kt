package com.growthook.aos.presentation.insight.actionplan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemActionplanBinding
import com.growthook.aos.domain.entity.Actionplan
import com.growthook.aos.util.extension.ItemDiffCallback

class ActionplanAdapter(
    private val clickModify: (Int) -> Unit,
    private val clickDelete: (Int) -> Unit,
    private val clickComplete: (Int) -> Unit,
) :
    ListAdapter<Actionplan, ActionplanAdapter.ActionplanViewHolder>(
        ItemDiffCallback<Actionplan>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.actionplanId == new.actionplanId },
        ),
    ) {

    inner class ActionplanViewHolder(
        private val binding: ItemActionplanBinding,
        private val clickModify: (Int) -> Unit,
        private val clickDelete: (Int) -> Unit,
        private val clickComplete: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var isItemSelected = false
        fun onBind(data: Actionplan) {
            with(binding) {
                tvActionplanTitle.text = data.content
                ivActionplanMenu.setOnClickListener {
                    isItemSelected = !isItemSelected
                    if (isItemSelected) {
                        clActionplanMenu.visibility = View.VISIBLE
                    } else {
                        clActionplanMenu.visibility = View.INVISIBLE
                    }
                }
                tvActionplanModify.setOnClickListener {
                    clActionplanMenu.visibility = View.INVISIBLE
                    clickModify(data.actionplanId)
                }
                tvActionplanDelete.setOnClickListener {
                    clActionplanMenu.visibility = View.INVISIBLE
                    clickDelete(data.actionplanId)
                }
                tvActionplanCompleteBtn.setOnClickListener {
                    clickComplete(data.actionplanId)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ActionplanViewHolder {
        val binding =
            ItemActionplanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActionplanViewHolder(binding, clickModify, clickDelete, clickComplete)
    }

    override fun onBindViewHolder(holder: ActionplanViewHolder, position: Int) {
        holder.onBind(getItem(position) as Actionplan)
    }
}

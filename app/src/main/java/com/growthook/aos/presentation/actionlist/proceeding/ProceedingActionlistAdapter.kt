package com.growthook.aos.presentation.actionlist.proceeding

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemProceedingActionplanBinding
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.util.extension.ItemDiffCallback

class ProceedingActionlistAdapter(
    private val clickSeedDetail: (Int) -> Unit,
    private val clickCompleted: (Int) -> Unit,
) :
    ListAdapter<ActionlistDetail, ProceedingActionlistAdapter.ProceedingActionlistViewHolder>(
        ItemDiffCallback<ActionlistDetail>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.actionplanId == new.actionplanId },
        ),
    ) {
    class ProceedingActionlistViewHolder(
        private val binding: ItemProceedingActionplanBinding,
        private val clickSeedDetail: (Int) -> Unit,
        private val clickCompleted: (Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ActionlistDetail) {
            with(binding) {
                tvProceedingActionplanTitle.text = data.content
                tvProceedingActionplanBtnRight.setOnClickListener {
                    clickCompleted(data.actionplanId)
                }
                tvProceedingActionplanBtnLeft.setOnClickListener {
                    Log.d("doing", "33333")
                    clickSeedDetail(data.seedId)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProceedingActionlistViewHolder {
        val binding =
            ItemProceedingActionplanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return ProceedingActionlistViewHolder(binding, clickSeedDetail, clickCompleted)
    }

    override fun onBindViewHolder(holder: ProceedingActionlistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as ActionlistDetail,
        )
    }
}

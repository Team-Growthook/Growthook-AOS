package com.growthook.aos.presentation.actionlist.proceeding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemProceedingActionplanBinding
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.util.extension.ItemDiffCallback

class ProceedingActionlistAdapter(
    private val clickSeedDetail: (Int) -> Unit,
    private val clickCompleted: (Int) -> Unit,
    private val clickScrap: () -> Unit,
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
        private val clickScrap: () -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ActionlistDetail) {
            with(binding) {
                tvProceedingActionplanTitle.text = data.content
                tvProceedingActionplanBtnRight.setOnClickListener {
                    clickCompleted(data.actionplanId)
                }
                tvProceedingActionplanBtnLeft.setOnClickListener {
                    clickSeedDetail(data.seedId)
                }
                if (data.isScraped) {
                    ivProceedingActionplan.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivProceedingActionplan.setImageResource(R.drawable.ic_scrap_unselected)
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
        return ProceedingActionlistViewHolder(binding, clickSeedDetail, clickCompleted, clickScrap)
    }

    override fun onBindViewHolder(holder: ProceedingActionlistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as ActionlistDetail,
        )
    }
}

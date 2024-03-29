package com.growthook.aos.presentation.todolist.completed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemCompletedActionplanBinding
import com.growthook.aos.domain.entity.ActionlistDetail
import com.growthook.aos.util.extension.ItemDiffCallback
import com.growthook.aos.util.extension.setOnSingleClickListener
import timber.log.Timber

class CompletedActionlistAdapter(
    private val clickSeedDetail: (Int) -> Unit,
    private val clickReviewDetail: (Int) -> Unit,
    private val clickSeed: (Int, Boolean) -> Unit,
) :
    ListAdapter<ActionlistDetail, CompletedActionlistAdapter.CompletedActionlistViewHolder>(
        ItemDiffCallback<ActionlistDetail>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.actionplanId == new.actionplanId },
        ),
    ) {
    class CompletedActionlistViewHolder(
        private val binding: ItemCompletedActionplanBinding,
        private val clickSeedDetail: (Int) -> Unit,
        private val clickReviewDetail: (Int) -> Unit,
        private val clickSeed: (Int, Boolean) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ActionlistDetail) {
            var isSeedSelected = data.isScraped
            with(binding) {
                tvCompletedActionplanTitle.text = data.content
                tvCompletedActionplanBtnLeft.setOnSingleClickListener {
                    clickSeedDetail(data.seedId)
                }
                tvCompletedActionplanBtnRight.setOnSingleClickListener {
                    clickReviewDetail(data.actionplanId)
                }
                if (data.isScraped) {
                    ivCompletedActionplan.setImageResource(R.drawable.ic_scrap_selected)
                } else {
                    ivCompletedActionplan.setImageResource(R.drawable.ic_scrap_unselected)
                }
                if (data.hasReview) {
                    tvCompletedActionplanBtnRight.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.White000,
                        ),
                    )
                    tvCompletedActionplanBtnRight.setBackgroundResource(R.drawable.rect_gray500_fill_5)
                    tvCompletedActionplanBtnRight.isClickable = true
                } else {
                    tvCompletedActionplanBtnRight.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.Gray300,
                        ),
                    )
                    tvCompletedActionplanBtnRight.setBackgroundResource(R.drawable.rect_gray600_fill_5)
                    tvCompletedActionplanBtnRight.isClickable = false
                }
                ivCompletedActionplan.setOnClickListener {
                    isSeedSelected = !isSeedSelected
                    clickSeed(data.actionplanId, isSeedSelected)
                    Timber.w("ivCompletedActionplan::: $isSeedSelected")
                    if (isSeedSelected) {
                        ivCompletedActionplan.setImageResource(R.drawable.ic_scrap_selected)
                    } else {
                        ivCompletedActionplan.setImageResource(R.drawable.ic_scrap_unselected)
                    }
                }
            }
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
        return CompletedActionlistViewHolder(binding, clickSeedDetail, clickReviewDetail, clickSeed)
    }

    override fun onBindViewHolder(holder: CompletedActionlistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as ActionlistDetail,
        )
    }
}

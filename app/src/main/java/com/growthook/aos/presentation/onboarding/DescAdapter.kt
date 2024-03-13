package com.growthook.aos.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.R
import com.growthook.aos.databinding.ItemDescExplainBinding
import com.growthook.aos.domain.entity.Explain

class DescAdapter : RecyclerView.Adapter<DescAdapter.DescViewHolder>() {

    val itemList: List<Explain> = listOf(
        Explain(
            "씨앗 심기",
            "내가 얻은 일상 속 영감, 인사이트를\n" +
                "한 줄로 표현해요.",
            R.drawable.ic_onboarding_desc1,
        ),
        Explain(
            "동굴 짓기",
            "나만의 인사이트를 보관할 동굴을 만들어요.",
            R.drawable.ic_onboarding_desc2,
        ),
        Explain("할 일 계획하기", "인사이트를 바탕으로 앞으로의 할 일을 기록해요.", R.drawable.ic_onboarding_desc3),
        Explain(
            "씨앗 잠금 해제",
            "기간이 지나 잠긴 씨앗은\n" +
                "할 일을 달성해 얻은 ‘쑥으로 잠금을 해제해요.",
            R.drawable.ic_onboarding_desc4,
        ),
    )

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescViewHolder {
        val binding: ItemDescExplainBinding =
            ItemDescExplainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DescViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescViewHolder, position: Int) {
        return holder.onBind(itemList[position])
    }

    class DescViewHolder(private val binding: ItemDescExplainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Explain) {
            binding.tvExplainTitle.text = item.title
            binding.tvExplainDesc.text = item.desc
            binding.ivExplain.setImageResource(item.image)
        }
    }
}

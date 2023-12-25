package com.growthook.aos.presentation.insight.noactionplan.add

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.growthook.aos.databinding.ItemActionplanEdittextBinding
import com.growthook.aos.util.extension.CommonTextWatcher

class AddActionplanAdapter(
    private val list: MutableList<String>,
    private val onAddItem: () -> Unit,
    private val onEditTextChanged: (position: Int, text: String) -> Unit,
) :
    RecyclerView.Adapter<AddActionplanAdapter.ActionplanEdittextViewHolder>() {

    inner class ActionplanEdittextViewHolder(
        private val binding: ItemActionplanEdittextBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var editText = binding.tilActionInsight
        var textWatcher: TextWatcher? = null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ActionplanEdittextViewHolder {
        val binding =
            ItemActionplanEdittextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return ActionplanEdittextViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 1 else list.size
    }

    override fun onBindViewHolder(holder: ActionplanEdittextViewHolder, position: Int) {
        holder.editText.editText?.setText(list[position])
        holder.editText.editText?.removeTextChangedListener(holder.textWatcher)
        holder.textWatcher = CommonTextWatcher(
            afterChanged = { editable ->
                onEditTextChanged.invoke(
                    holder.absoluteAdapterPosition,
                    editable?.toString().orEmpty(),
                )
            },
        )
        holder.editText.editText?.addTextChangedListener(holder.textWatcher)
    }

    fun addItem(item: String) {
        list.add(0, item)
        onAddItem.invoke()
        notifyItemInserted(0)
    }

    companion object {
        const val TAG = "adapter"
    }
}

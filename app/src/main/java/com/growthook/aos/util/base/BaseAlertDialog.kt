package com.growthook.aos.util.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.growthook.aos.util.dialog.LeftIntendedAlertDialog
import com.growthook.aos.util.dialog.RightIntendedAlertDialog
import com.growthook.aos.util.dialog.SingleIntendedAlertDialog
import com.growthook.aos.util.extension.getWidthProportionalToDevice

abstract class BaseAlertDialog : DialogFragment() {
    protected var _binding: ViewBinding? = null
    protected var title: String? = null
    protected var description: String? = null
    protected var positiveText: String? = null
    protected var negativeText: String? = null
    protected lateinit var positiveAction: () -> Unit
    protected lateinit var negativeAction: () -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setSize() {
        val dialogWidth = getWidthProportionalToDevice(requireContext(), WIDTH_RATE)
        dialog?.window?.setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun setBackgroundTransparent() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    abstract fun setTitle()
    abstract fun setDescription()
    abstract fun setPositiveText()
    abstract fun setNegativeText()
    abstract fun setPositiveClick(action: () -> Unit)
    abstract fun setNegativeClick(action: () -> Unit)

    class Builder() {
        private var isCancelable = true

        fun build(
            type: DialogType,
            title: String,
            description: String,
            positiveText: String,
            negativeText: String,
            positiveAction: () -> Unit,
            negativeAction: () -> Unit,
        ): BaseAlertDialog {
            return type.getInstance().apply {
                this.title = title
                this.description = description
                this.positiveText = positiveText
                this.negativeText = negativeText
                this.positiveAction = positiveAction
                this.negativeAction = negativeAction
                this.isCancelable = this@Builder.isCancelable
            }
        }

        fun setCancelable(isCancelable: Boolean): Builder {
            this.isCancelable = isCancelable
            return this
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    enum class DialogType {
        RIGHT_INTENDED {
            override fun getInstance(): BaseAlertDialog = RightIntendedAlertDialog()
        },
        LEFT_INTENDED {
            override fun getInstance(): BaseAlertDialog = LeftIntendedAlertDialog()
        },
        SINGLE_INTENDED {
            override fun getInstance(): BaseAlertDialog = SingleIntendedAlertDialog()
        },
//        RIGHT_INTENDED_WITH_INNER_VIEW {
//            override fun getInstance(): BaseAlertDialog = RightIntendedWithInnerViewAlertDialog()
//        },
//        SINGLE_INTENDED_WITH_INNER_VIEW {
//            override fun getInstance(): BaseAlertDialog = SingleIntendedWithInnerViewAlertDialog()
//        },
        ;

        abstract fun getInstance(): BaseAlertDialog
    }

    companion object {
        private const val WIDTH_RATE = 0.85f
    }
}

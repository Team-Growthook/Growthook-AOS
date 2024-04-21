package com.growthook.aos.presentation.cavecreate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.growthook.aos.databinding.FragmentCreateCaveBinding
import com.growthook.aos.util.base.BaseAlertDialog
import com.growthook.aos.util.base.BaseFragment
import com.growthook.aos.util.extension.CommonTextWatcher
import com.growthook.aos.util.extension.setOnSingleClickListener

class CreateCaveFragment : BaseFragment<FragmentCreateCaveBinding>() {
    private val viewModel by viewModels<CreateNewCaveViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentCreateCaveBinding = FragmentCreateCaveBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMakeCaveView()
        initGetCaveContent()
        initClickCloseBtn()
    }

    private fun initMakeCaveView() {
        clickOpenSwitchBtn()
        setBtnEnabled()
    }

    private fun initClickCloseBtn() {
        binding.btnCaveClose.setOnClickListener {
        }
    }

    private fun clickOpenSwitchBtn() {
        binding.switchOpen.setOnCheckedChangeListener { CompoundButton, onSwitch ->
            if (onSwitch) {
                BaseAlertDialog.Builder()
                    .setCancelable(false)
                    .build(
                        type = BaseAlertDialog.DialogType.SINGLE_INTENDED,
                        title = "내 동굴에 친구를 초대해\n  인사이트를 공유해요!",
                        description = "해당 기능은 추후 업데이트 예정이에요 :)",
                        isTipVisility = false,
                        isRemainThookVisility = false,
                        isBackgroundImageVisility = false,
                        isDescriptionVisility = true,
                        positiveText = "확인",
                        negativeText = "",
                        tipText = "",
                        negativeAction = {},
                        positiveAction = {},
                        remainThookText = "",
                    ).show(parentFragmentManager, "delete dialog")
                binding.switchOpen.isChecked = false
            } else {
                //
            }
        }
    }

    private fun initGetCaveContent() {
        val nameTextWatcher = CommonTextWatcher(afterChanged = { edtName ->
            viewModel.getCaveName(edtName.toString())
        })
        val introductionTextWatcher = CommonTextWatcher(afterChanged = { edtIntroduction ->
            viewModel.getCaveIntroduction(edtIntroduction.toString())
        })
        with(binding) {
            edtCaveName.addTextChangedListener(nameTextWatcher)
            edtCaveIntroduction.addTextChangedListener(introductionTextWatcher)
        }
    }

    private fun setBtnEnabled() {
        viewModel.checkBtnEnabled.observe(viewLifecycleOwner) {
            with(binding.btnCaveCreate) {
                if (it) {
                    isEnabled = true
                    clickCreateCaveBtn()
                } else {
                    isEnabled = false
                }
            }
        }
    }

    private fun clickCreateCaveBtn() {
        binding.btnCaveCreate.setOnSingleClickListener {
            postNewCave()
        }
    }

    private fun postNewCave() {
        viewModel.postNewCave()
        viewModel.postCaveSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                sendNewCaveData()
                Toast.makeText(requireContext(), "새 동굴을 만들었어요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendNewCaveData() {
        /*
        SeeNewCaveActivity도 프래그먼트화하자
         */
//        val newCaveIntent = NewCaveIntent(
//            binding.edtCaveName.text.toString(),
//            binding.edtCaveIntroduction.text.toString(),
//        )

//        startActivity(SeeNewCaveActivity.getIntent(this, newCaveIntent))
//        finish()
    }
}

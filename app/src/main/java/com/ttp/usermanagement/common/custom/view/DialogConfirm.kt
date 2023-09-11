package com.ttp.usermanagement.common.custom.view

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.ttp.usermanagement.R
import com.ttp.usermanagement.databinding.DialogConfirmBinding

class DialogConfirm(
    mContext: Context,
    mTitle: String = "Confirm?",
    private val callBack: (result: Int) -> Unit
) : Dialog(mContext) {

    private var binding: DialogConfirmBinding = DialogConfirmBinding.inflate(layoutInflater)

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window!!.decorView.setBackgroundResource(R.color.transparent)
        val window = window
        val wlp = window!!.attributes
        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        window.attributes.windowAnimations = R.style.DialogAnimation
        getWindow()!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        setCancelable(true)
        setCanceledOnTouchOutside(true)

        binding.tvTitleDialog.text = mTitle

        binding.dialog.setOnClickListener {
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            callBack.invoke(0)
            dismiss()
        }

        binding.btnSuccess.setOnClickListener {
            callBack.invoke(1)
            dismiss()
        }
    }
}
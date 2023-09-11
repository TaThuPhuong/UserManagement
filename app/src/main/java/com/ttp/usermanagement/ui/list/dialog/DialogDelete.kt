package com.ttp.usermanagement.ui.list.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.ttp.usermanagement.R
import com.ttp.usermanagement.data.model.UserInfo
import com.ttp.usermanagement.databinding.DialogDeleteBinding

class DialogDelete(
    mContext: Context,
    userInfo: UserInfo,
    private val callBack: (result: Int) -> Unit
) : Dialog(mContext) {

    private var binding: DialogDeleteBinding = DialogDeleteBinding.inflate(layoutInflater)

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

        binding.user = userInfo

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
package com.ttp.usermanagement.common.custom.view

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.ttp.usermanagement.R
import com.ttp.usermanagement.databinding.DialogAlertBinding

fun Context.showAutoDismissDialog(message: String, durationMs: Long? = null) {
    val dialog = Dialog(this).apply {
        val binding = DialogAlertBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        window?.setBackgroundDrawableResource(R.color.transparent)

        val wlp = window?.attributes
        wlp?.gravity = Gravity.CENTER
        wlp?.flags = wlp!!.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window?.attributes = wlp
        window?.attributes?.windowAnimations = R.style.DialogAnimation
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )

        binding.imvDismiss.setImageResource(android.R.drawable.ic_delete)
        binding.tvTitleDialog.text = message
        binding.imvDismiss.setOnClickListener {
            dismiss()
        }
        binding.dialog.setOnClickListener {
            dismiss()
        }

        durationMs?.let {
            Handler(mainLooper).postDelayed({
                dismiss()
            }, it)
        }
    }
    dialog.show()
}

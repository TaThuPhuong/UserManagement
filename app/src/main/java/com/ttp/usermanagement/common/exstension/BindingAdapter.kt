package com.ttp.usermanagement.common.exstension

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import com.ttp.usermanagement.common.util.ScreenUtils

@BindingAdapter("set_visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("height_status")
fun setSizeToolbar(view: View, typeParent: Int) {
    view.layoutParams = when (typeParent) {
        0 -> {
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.getActionBarHeight(view.context)
            )
        }

        1 -> {
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.getActionBarHeight(view.context)
            )
        }

        else -> {
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ScreenUtils.getActionBarHeight(view.context)
            )
        }
    }
}

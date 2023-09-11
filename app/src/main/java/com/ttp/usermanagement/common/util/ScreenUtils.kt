package com.ttp.usermanagement.common.util

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.ttp.usermanagement.R

object ScreenUtils {

    private fun getHeightStatusLightBar(context: Context): Int {
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            val heightStatusBar = context.resources.getDimensionPixelSize(resourceId)
            val defaultHeight =
                context.resources.getDimension(R.dimen.default_status_bar_height).toInt()
            if (heightStatusBar > defaultHeight) {
                defaultHeight
            } else {
                heightStatusBar
            }
        } else {
            context.resources.getDimension(R.dimen.default_status_bar_height).toInt()
        }
    }

    fun getActionBarHeight(context: Context): Int {
        val actionBarHeight =
            context.resources.getDimensionPixelSize(R.dimen.default_action_bar_height)
        val statusBarHeight = getHeightStatusLightBar(context)
        return actionBarHeight + statusBarHeight
    }

    fun View.setMarginsStatusBar(context: Context) {
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            val p = layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, getHeightStatusLightBar(context), 0, 0)
            requestLayout()
        }
    }

}
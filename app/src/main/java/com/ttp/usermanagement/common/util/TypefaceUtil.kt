package com.ttp.usermanagement.common.util

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.ttp.usermanagement.R

object TypefaceUtil {
    fun getMedium(context: Context): Typeface {
        return ResourcesCompat.getFont(context, R.font.poppins_medium)!!
    }

    fun getSemiBold(context: Context): Typeface {
        return ResourcesCompat.getFont(context, R.font.poppins_semi_bold)!!
    }

    fun getRegular(context: Context): Typeface {
        return ResourcesCompat.getFont(context, R.font.poppins)!!
    }
}
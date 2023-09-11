package com.ttp.usermanagement.common.custom.font

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ttp.usermanagement.common.util.TypefaceUtil.getMedium

class TextViewPoppinsMedium : AppCompatTextView {
    constructor(context: Context) : super(context) {
        initFont(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initFont(context)
    }

    private fun initFont(context: Context) {
        typeface = getMedium(context)
    }
}
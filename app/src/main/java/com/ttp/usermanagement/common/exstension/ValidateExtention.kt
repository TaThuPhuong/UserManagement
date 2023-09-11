package com.ttp.usermanagement.common.exstension

import android.content.Context
import android.widget.EditText
import com.ttp.usermanagement.R

fun setErrorIfInvalid(field: EditText, errorMsg: String): Boolean {
    var isValid = true
    if (field.text.isEmpty()) {
        field.error = errorMsg
        isValid = false
    }
    return isValid
}

fun setErrorIfLengthExceeded(
    field: EditText,
    maxLength: Int,
    errorMsg: String,
    context: Context
): Boolean {
    var isValid = true
    if (field.text.length > maxLength) {
        field.error = "$errorMsg ${context.getString(R.string.length_err, maxLength)}"
        isValid = false
    }
    return isValid
}
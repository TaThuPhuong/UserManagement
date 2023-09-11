package com.ttp.usermanagement.common.exstension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.isShowing(): Boolean {
    return visibility == View.VISIBLE
}

fun View.showSnackBar(snackBarText: String) {
    Snackbar.make(this, snackBarText, Snackbar.LENGTH_SHORT).show()
}

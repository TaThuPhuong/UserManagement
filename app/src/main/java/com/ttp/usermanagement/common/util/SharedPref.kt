package com.ttp.usermanagement.common.util

import android.content.Context

object SharedPref {
    private const val PREFERENCES_NAME = "userManagement"

    fun setLogin(context: Context, value: Boolean) {
        val sharePref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharePref.edit()
        editor.putBoolean("logged", value).apply()
    }

    fun isLogged(context: Context): Boolean {
        val sharePref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharePref.getBoolean("logged", false)
    }
}
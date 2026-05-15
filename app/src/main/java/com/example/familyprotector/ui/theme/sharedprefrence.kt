package com.example.familyprotector.ui.theme

import android.content.Context
import android.content.SharedPreferences

object SharedPreference {

    private const val PREF_NAME = "APP_PREF"
    private const val KEY_LOGIN = "login"

    private lateinit var pref: SharedPreferences

    fun init(context: Context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setLogin(value: Boolean) {
        pref.edit().putBoolean(KEY_LOGIN, value).apply()
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_LOGIN, false)
    }
}
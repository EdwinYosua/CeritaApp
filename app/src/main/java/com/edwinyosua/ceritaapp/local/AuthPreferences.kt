package com.edwinyosua.ceritaapp.local

import android.content.Context

class AuthPreferences(context: Context) {

    companion object {
        private const val PREF_FILE_NAME = "auth_preferences"
        private const val PREF_KEY_AUTH_TOKEN = "token"
    }

    private val preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    fun saveLoginToken(token: String) {
        preferences.edit().putString(PREF_KEY_AUTH_TOKEN, token).apply()
    }
    fun getLoginToken(): String? = preferences.getString(PREF_KEY_AUTH_TOKEN, null)

    fun removeLoginToken() {
        preferences.edit().remove(PREF_KEY_AUTH_TOKEN).apply()
    }
}
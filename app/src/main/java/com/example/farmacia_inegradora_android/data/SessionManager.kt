package com.example.farmacia_inegradora_android.data

import android.content.Context
import android.content.SharedPreferences
import com.example.farmacia_inegradora_android.responses.UserData
import com.google.gson.Gson

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("pharmacy_session", Context.MODE_PRIVATE)
    private val gson = Gson()
    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER = "user_data"
    }

    fun saveSession(token: String, user: UserData) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putString(KEY_USER, gson.toJson(user))
            apply()
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getUser(): UserData? {
        val userJson = prefs.getString(KEY_USER, null)
        return if (userJson != null) {
            gson.fromJson(userJson, UserData::class.java)
        } else null
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = getToken() != null
}

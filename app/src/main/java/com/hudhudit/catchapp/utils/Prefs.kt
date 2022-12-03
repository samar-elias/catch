package com.hudhudit.catchapp.utils

import android.content.SharedPreferences
import androidx.core.content.edit

import java.util.*
import javax.inject.Inject

class Prefs @Inject constructor(
    private val sharedPrefs: SharedPreferences,
) {
    var language: String?
        get() {
            return sharedPrefs.getString(AppConstants.defaultLanguageKey, Locale.getDefault().language)
                ?: Locale.getDefault().language
        }
        set(value) {
            sharedPrefs.edit { putString(AppConstants.defaultLanguageKey, value) }
        }

    var isLoggedIn: Boolean
        get() = sharedPrefs.getBoolean(AppConstants.isLoggedInKey, false)
        set(value) = sharedPrefs.edit { putBoolean(AppConstants.isLoggedInKey, value)  ?: Locale.getDefault() }

}
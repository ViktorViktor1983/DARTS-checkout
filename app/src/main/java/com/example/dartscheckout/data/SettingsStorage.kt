package com.example.dartscheckout.data

import android.content.Context

object SettingsStorage {
    private const val PREFS = "darts_settings"
    private const val KEY_SORT_DESC = "sort_desc"

    fun isSortDescending(context: Context): Boolean =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_SORT_DESC, false)

    fun setSortDescending(context: Context, value: Boolean) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_SORT_DESC, value).apply()
    }
}

package com.android.myapplication.movies.persistence

import android.content.Context

private const val PREF_CATEGORY = "pref_category"
private const val PREF_QUERY = "pref_query"
object PreferencesStorage {

    fun getStoredQuery(context: Context):String?{
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_QUERY,null)
    }

    fun setStoredQuery(context: Context, value: String?) {
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_QUERY, value)
            .apply()
    }

    fun getStoredCategory(context: Context): String{
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(PREF_CATEGORY,"popular")!!
    }

    fun setStoredCategory(context: Context, value: String) {
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_CATEGORY, value)
            .apply()
    }

}
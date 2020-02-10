package com.android.myapplication.movies.persistence

import android.content.Context
import com.android.myapplication.movies.util.Category

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

    fun getStoredCategory(context: Context): Category{
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val valueString =  prefs.getString(PREF_CATEGORY,Category.POPULAR.toString())
        return when(valueString){
            Category.TOPRATED.toString()->Category.TOPRATED
            Category.UPCOMING.toString()->Category.UPCOMING
            else -> Category.POPULAR
        }
    }

    fun setStoredCategory(context: Context, value: Category) {
        val valueString = value.toString()
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(PREF_CATEGORY, valueString)
            .apply()
    }

}
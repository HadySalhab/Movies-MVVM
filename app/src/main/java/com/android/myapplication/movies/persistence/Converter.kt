package com.android.myapplication.movies.persistence

import android.util.Log
import androidx.room.TypeConverter
import com.android.myapplication.movies.util.Category
import com.android.myapplication.popularmovies.api.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun catToInt(cat: Category?): Int? {
        return cat?.ordinal
    }

    @TypeConverter
    fun intToCat(int: Int?): Category? {
        return int?.let {
            when (it) {
                0 -> Category.POPULAR
                1 -> Category.UPCOMING
                2 -> Category.TOPRATED
                else -> Category.POPULAR
            }
        }
    }

}
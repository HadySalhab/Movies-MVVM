package com.android.myapplication.movies.persistence

import android.util.Log
import androidx.room.TypeConverter
import com.android.myapplication.movies.util.Category
import com.android.myapplication.popularmovies.api.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    companion object {
        private const val TAG = "Converter"
    }
    private val gson = Gson()
    @TypeConverter
    fun fromGenresList(genres: List<Genre>?): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenresList(genres: String?): List<Genre>? {
        if (genres == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Genre?>?>() {}.type
        return gson.fromJson(genres, listType)

    }

    @TypeConverter
    fun catToInt(cat: Category?): Int?{
        return cat?.ordinal
    }
    @TypeConverter
    fun intToCat(int:Int?):Category?{
        return  int?.let {
             when(it){
                0->Category.POPULAR
                1->Category.UPCOMING
                2->Category.TOPRATED
                else -> Category.POPULAR
            }
        }
    }

  /*  @TypeConverter
    fun stringToCategory(stringToCat: String?): Category? {
        return stringToCat.let {
            Log.d(TAG, "stringToCategory: ${stringToCat}")
            when (it) {
                Category.POPULAR.toString() -> Category.POPULAR
                Category.UPCOMING.toString() -> Category.UPCOMING
                Category.TOPRATED.toString() -> Category.TOPRATED
                else -> Category.POPULAR
            }
        }
    }*/

}
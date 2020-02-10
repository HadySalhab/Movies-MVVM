package com.android.myapplication.movies.persistence

import androidx.room.TypeConverter
import com.android.myapplication.movies.util.Category
import com.android.myapplication.popularmovies.api.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()
    @TypeConverter
    fun fromGenresList(genres: List<Genre>): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenresList(genres: String?): List<Genre> {
        return if (genres == null) {
            emptyList()
        } else {
            val listType = object : TypeToken<List<Genre?>?>() {}.type
            gson.fromJson(genres, listType)
        }
    }

    @TypeConverter
    fun categoryToString(cat: Category) = cat.toString()

    @TypeConverter
    fun stringToCategory(stringToCat: String?) = stringToCat?.let(Category::valueOf)

}
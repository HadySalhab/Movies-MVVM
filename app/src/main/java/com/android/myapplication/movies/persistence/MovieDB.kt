package com.android.myapplication.movies.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.myapplication.movies.models.MovieAndDetails
import com.android.myapplication.movies.persistence.dao.MovieAndDetailsDao
import com.android.myapplication.popularmovies.api.model.Cast
import com.android.myapplication.popularmovies.api.model.Movie
import com.android.myapplication.popularmovies.api.model.Review
import com.android.myapplication.popularmovies.api.model.Video

@Database(
    entities = [Movie::class, Video::class, Cast::class, Review::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
abstract class MovieDB : RoomDatabase() {
    abstract val movieDao: MovieAndDetailsDao
}
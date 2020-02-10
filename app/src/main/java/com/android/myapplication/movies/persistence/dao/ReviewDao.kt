package com.android.myapplication.movies.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.myapplication.popularmovies.api.model.Review

@Dao
interface ReviewDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListReviews(reviews: List<Review?>?)
}
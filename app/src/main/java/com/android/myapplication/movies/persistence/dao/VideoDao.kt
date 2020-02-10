package com.android.myapplication.movies.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.myapplication.popularmovies.api.model.Video

@Dao
interface VideoDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListVideos(videos: List<Video?>?)
}
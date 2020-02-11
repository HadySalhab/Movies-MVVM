package com.android.myapplication.movies.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.myapplication.movies.models.MovieAndDetails
import com.android.myapplication.movies.util.Category
import com.android.myapplication.popularmovies.api.model.Movie

@Dao
interface MovieAndDetailDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(vararg movie: Movie):LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie:Movie)

    @Query("SELECT * FROM movie WHERE category=:category ORDER BY primary_key ASC LIMIT (:pageNumber*20)")
    fun getMovies(pageNumber: Int,category: Category):LiveData<List<Movie>>

    /*LIST VIEW STATE*/
    @Query("SELECT * FROM movie WHERE title LIKE '%' || :query || '%' ORDER BY primary_key DESC LIMIT (:pageNumber*20) ")
    fun searchListMovie(query: String,pageNumber:Int):LiveData<List<Movie>>

    /*DETAIL VIEW STATE*/
    @Transaction
    @Query("SELECT * FROM movie WHERE id= :movieId")
    fun getMovieAndDetails(movieId:Long):LiveData<MovieAndDetails>

}
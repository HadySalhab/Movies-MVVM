package com.android.myapplication.movies.persistence.dao

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.myapplication.movies.models.MovieAndDetails
import com.android.myapplication.movies.util.Category
import com.android.myapplication.popularmovies.api.model.*

@Dao
interface MovieAndDetailsDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListCasts(castList: List<Cast?>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListReviews(reviews: List<Review?>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListVideos(videos: List<Video?>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListMovies(movies:List<Movie?>?):LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(vararg movie: Movie):LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Update
    fun updateMovie(movie:Movie)

    /*LIST VIEW STATE*/
    @Query("SELECT * FROM movie WHERE category=:category LIMIT (:pageNumber*19)")
    fun getListMovie(category: Category,pageNumber:Int):LiveData<List<Movie>>

    /*LIST VIEW STATE*/
    @Query("SELECT * FROM movie WHERE title LIKE '%' || :query || '%' ORDER BY vote_average DESC LIMIT (:pageNumber*19) ")
    fun searchListMovie(query: String,pageNumber:Int):LiveData<List<Movie>>

    /*DETAIL VIEW STATE*/
    @Query("SELECT * FROM movie WHERE id= :movieId")
    fun getMovieAndDetails(movieId:Long):LiveData<MovieAndDetails>

}
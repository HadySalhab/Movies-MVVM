package com.android.myapplication.movies.models

import java.util.ArrayList

import androidx.room.Embedded
import androidx.room.Relation
import com.android.myapplication.popularmovies.api.model.Cast
import com.android.myapplication.popularmovies.api.model.Movie
import com.android.myapplication.popularmovies.api.model.Review
import com.android.myapplication.popularmovies.api.model.Video

/**
 * This class is used to load full movie details including (Trailers, Cast, etc)
 *
 * @author Yassin Ajdi
 * @since 11/12/2018.
 */
data class MovieAndDetails (
    @Embedded
    var movie: Movie? = null,

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    var trailers: List<Video> = ArrayList(),

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    var castList: List<Cast> = ArrayList(),

    @Relation(parentColumn = "id", entityColumn = "movie_id")
    var reviews: List<Review> = ArrayList()
)

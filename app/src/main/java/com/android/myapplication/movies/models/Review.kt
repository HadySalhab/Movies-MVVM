package com.android.myapplication.popularmovies.api.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "review",
    foreignKeys = [ForeignKey(entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)],
    indices = [Index(value = ["movie_id"])])
data class Review(
    @PrimaryKey
    @SerializedName("id")
    val id:String=UUID.randomUUID().toString(),
    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("url")
    val url:String?=null
)
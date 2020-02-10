package com.android.myapplication.popularmovies.api.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "review",
    foreignKeys = [ForeignKey(entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )])
data class Review(
    @PrimaryKey
    @SerializedName("id")
    val id:String?=null,
    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("url")
    val url:String?=null
)
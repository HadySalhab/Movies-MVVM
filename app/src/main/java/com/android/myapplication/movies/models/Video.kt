package com.android.myapplication.popularmovies.api.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName


@Entity(tableName = "video",
    foreignKeys = [ForeignKey(entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movie_id"],
        onDelete = CASCADE,
        onUpdate = CASCADE)])

data class Video(
    @PrimaryKey
    @SerializedName("id")
    val id:String?=null,
    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0,
    @SerializedName("key")
    val key:String?=null,
    @SerializedName("name")
    val name:String?=null,
    @SerializedName("site")
    val site:String?=null
)
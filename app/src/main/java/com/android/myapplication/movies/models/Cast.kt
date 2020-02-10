package com.android.myapplication.popularmovies.api.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cast",foreignKeys = [ForeignKey(entity = Movie::class,
    parentColumns = ["id"],
    childColumns = ["movie_id"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["movie_id"])])
data class Cast(
    @PrimaryKey
    @ColumnInfo(name="cast_id")
    @SerializedName("cast_id")
    val castId:Int=0,

    @SerializedName("name")
    val name:String?=null,

    @ColumnInfo(name="profile_path")
    @SerializedName("profile_path")
    val profilePath:String?=null,

    @SerializedName("character")
    val character:String?=null,

    @ColumnInfo(name = "movie_id")
    var movieId: Long = 0
    )
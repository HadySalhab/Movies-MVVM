package com.android.myapplication.movies.util

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
    val status:Status,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(Status.SUCCESS,data)
    class Loading<T>(data: T? = null) : Resource<T>(Status.LOADING,data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(Status.ERROR,data, message)
    public enum class Status{SUCCESS,ERROR,LOADING}
}
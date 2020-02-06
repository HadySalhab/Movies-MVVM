package com.android.myapplication.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.popularmovies.api.responses.MoviesResponse
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity :BaseActivity(){
    val movieApi:MoviesApi by inject()

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testRetrofitRequest()
    }

    fun testRetrofitRequest(){
        val responseCall = movieApi.getPopularMovies()
        responseCall.enqueue(object:Callback<MoviesResponse>{
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: server response failed ${t.message}}")
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                Log.d(TAG, "onResponse: server response: ${response} ")
                if(response.code() == 200){
                    Log.d(TAG,"onResponse: ${response.body()}")
                    val movies = response.body()?.movies
                    movies?.let {
                        movies.forEach { movie->
                            Log.d(TAG,"movie: ${movie}")
                        }
                    }
                } else{
                    try {
                        Log.d(TAG,"onResponse: ${response.errorBody()}")
                    }catch (e:IOException){
                        e.printStackTrace()
                    }
                }
            }

        })
    }
}

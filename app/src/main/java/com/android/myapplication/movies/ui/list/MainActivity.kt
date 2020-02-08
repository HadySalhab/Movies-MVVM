package com.android.myapplication.movies.ui.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.myapplication.movies.R
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.ui.detail.DetailActivity
import org.koin.android.ext.android.inject

class MainActivity :AppCompatActivity(),
    MovieListFragment.Callbacks{
    val movieApi:MoviesApi by inject()
    private lateinit var toolbar:Toolbar
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_container,
                    MovieListFragment()
                )
                .commit()
        }
    }
    private fun setUpToolbar(){
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onMovieClick(movieId: Long) {
        Log.d(TAG, "onMovieClick: ")
        startActivity(DetailActivity.getIntent(movieId,this))
    }


//    fun testRetrofitRequest(){
//        val responseCall = movieApi.getMovieDetail(id=157336)
//        responseCall.enqueue(object:Callback<MovieDetailsResponse>{
//            override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
//                Log.d(TAG, "onFailure: server response failed ${t.message}}")
//            }
//
//            override fun onResponse(
//                call: Call<MovieDetailsResponse>,
//                response: Response<MovieDetailsResponse>
//            ) {
//                Log.d(TAG, "onResponse: server response: ${response} ")
//                if(response.code() == 200){
//                    Log.d(TAG,"onResponse: ${response.body()}")
//                    val moviesTitle = response.body()?.title
//                    moviesTitle?.let {
//                        moviesTitle.forEach { moviesTitle->
//                            Log.d(TAG,"movie: ${moviesTitle}")
//                        }
//                    }
//                } else{
//                    try {
//                        Log.d(TAG,"onResponse: ${response.errorBody()}")
//                    }catch (e:IOException){
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//        })
//    }
}

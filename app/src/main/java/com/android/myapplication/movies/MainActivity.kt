package com.android.myapplication.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.ui.MovieListFragment
import com.android.myapplication.popularmovies.api.responses.MovieDetailsResponse
import com.android.myapplication.popularmovies.api.responses.MoviesResponse
import com.google.android.material.appbar.AppBarLayout
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity :AppCompatActivity(),MovieListFragment.Callbacks{
    val movieApi:MoviesApi by inject()
    private lateinit var toolbar:Toolbar
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var appBarLayout:AppBarLayout

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()
    }
    private fun setUpToolbar(){
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        appBarLayout = findViewById(R.id.main_appbarlayout)
        navController = findNavController(R.id.nav_host)
        appBarConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfig)
    }

    override fun onMovieListFragmentDisplayed() {
        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.setScrollFlags(
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                    or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
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

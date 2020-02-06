package com.android.myapplication.movies

import android.app.Application
import com.android.myapplication.movies.api.MoviesApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application(){
    private val appModule = module{
        single<MoviesApi> {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MoviesApi::class.java)
        }

    }
}
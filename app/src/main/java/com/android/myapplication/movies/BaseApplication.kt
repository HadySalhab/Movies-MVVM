package com.android.myapplication.movies

import BASE_URL
import android.app.Application
import com.android.myapplication.movies.api.MoviesApi
import com.android.myapplication.movies.viewmodels.MovieListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseApplication : Application() {
    private val appModule = module {
        single<MoviesApi> {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MoviesApi::class.java)
        }

        viewModel<MovieListViewModel> {
            MovieListViewModel()
        }

    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }
}
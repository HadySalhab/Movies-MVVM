package com.android.myapplication.movies

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val INTENT_EXTRA = "movie_id"

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val id = intent?.extras?.getLong(INTENT_EXTRA)
        getDetails(id)
        Log.d(TAG, "onCreate: ${id}")
        viewModel.detailMovie.observe(this, Observer { response ->
            Log.d(TAG, "onStart:${
            response.id
            } ")
        })
    }

    fun getDetails(id: Long?) {
        id?.let {
            Log.d(TAG, "getDetails: ")
            viewModel.getDetails(id)
        }

    }

    companion object {
        private const val TAG = "DetailActivity"
        fun getIntent(movieId: Long, context: Context): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA, movieId)
            return intent
        }
    }
}

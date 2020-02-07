package com.android.myapplication.movies.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.ActivityDetailBinding
import com.android.myapplication.movies.util.RecyclerViewDecoration
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IndexOutOfBoundsException

private const val INTENT_EXTRA = "movie_id"

class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailActivityViewModel by viewModel()
    private lateinit var binding:ActivityDetailBinding
    private lateinit var viewPager:ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_detail
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initViewPager()
        initCastRecyclerView()
        val id = intent?.extras?.getLong(INTENT_EXTRA)
        getDetails(id)
        Log.d(TAG, "onCreate: ${id}")
        viewModel.detailMovieResponse.observe(this, Observer { response ->
            Log.d(
                TAG, "onStart:${
            response.id
            } ")
        })
    }


    fun initViewPager(){
        viewPager=binding.movieDetailsInfo.viewpager
        tabLayout = binding.movieDetailsInfo.tabs
        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    fun initCastRecyclerView(){
        binding.movieDetailsInfo.listCast.apply {
            adapter = CastAdapter()
            layoutManager = LinearLayoutManager(this@DetailActivity,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(RecyclerViewDecoration(16))
        }
    }

    fun getDetails(id: Long?) {
        id?.let {
            Log.d(TAG, "getDetails: ")
            viewModel.getDetails(id)
        }

    }
    private fun getTabTitle(position: Int):String? =
        when(position){
            REVIEW_LIST_PAGE_INDEX->getString(R.string.tab_reviews)
            TRAILER_LIST_PAGE_INDEX->getString(R.string.tab_trailers)
            else->null
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

package com.android.myapplication.movies.ui.detail.fragments


import YOUTUBE_BASE_URL
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.FragmentDetailBinding
import com.android.myapplication.movies.ui.detail.*
import com.android.myapplication.movies.util.EventObserver
import com.android.myapplication.movies.util.RecyclerViewDecoration
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
private const val MOVIE_ID_EXTRA="movie_id_extra"
class DetailFragment : Fragment() {
    private val viewModel: DetailFragmentViewModel by viewModel()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var id:Long?=null

    companion object{
        fun getInstance(movieId:Long):DetailFragment{
            val args = Bundle()
            args.putLong(MOVIE_ID_EXTRA,movieId)
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        id = bundle?.getLong(MOVIE_ID_EXTRA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initViewPager()
        initCastRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerObserver()
        getDetails(id)
    }

    fun registerObserver(){
        viewModel.showVideo.observe(this, EventObserver {
            val videoList = viewModel.trailersDetails.value
            if (!videoList.isNullOrEmpty()) {
                val video = videoList.get(0)
                val videoKey = video.key
                videoKey?.let {
                    val appIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${video.key}"))
                    if (appIntent.resolveActivity(requireActivity().packageManager) != null) {
                        startActivity(appIntent)
                    } else {
                        val webIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + video.key))
                        if (webIntent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivity(webIntent)
                        }
                    }
                }
            }
        })
    }

    fun initViewPager() {
        viewPager = binding.movieDetailsInfo.viewpager
        tabLayout = binding.movieDetailsInfo.tabs
        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    fun getDetails(id: Long?) {
        id?.let {
            viewModel.getDetails(id)
        }
    }

    fun initCastRecyclerView() {
        binding.movieDetailsInfo.listCast.apply {
            adapter = CastAdapter()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(RecyclerViewDecoration(16))
        }
    }
    private fun getTabTitle(position: Int): String? =
        when (position) {
            REVIEW_LIST_PAGE_INDEX -> getString(R.string.tab_reviews)
            TRAILER_LIST_PAGE_INDEX -> getString(R.string.tab_trailers)
            else -> null
        }


}

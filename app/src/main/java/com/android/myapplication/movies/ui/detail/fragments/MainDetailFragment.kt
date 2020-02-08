package com.android.myapplication.movies.ui.detail.fragments


import PAGE_CAST
import PAGE_COUNT
import PAGE_INFO
import PAGE_REVIEW
import PAGE_VIDEOS
import YOUTUBE_BASE_URL
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.myapplication.movies.databinding.FragmentMainDetailBinding
import com.android.myapplication.movies.util.EventObserver
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
private const val MOVIE_ID_EXTRA = "movie_id_extra"

class DetailFragment : Fragment() {
    private val viewModel: DetailFragmentViewModel by viewModel()
    private lateinit var binding: FragmentMainDetailBinding
    private lateinit var viewPager: ViewPager2
   private lateinit var tabLayout: TabLayout
    private var id: Long? = null

    companion object {
        fun getInstance(movieId: Long?): DetailFragment {
            val args = Bundle()
            movieId?.let {
                args.putLong(MOVIE_ID_EXTRA, movieId)
            }
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
        binding = FragmentMainDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initViewPager()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        registerObserver()
        getDetails(id)
    }

    fun registerObserver() {
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
        viewPager = binding.contentDetail.viewpager
        tabLayout = binding.contentDetail.tabs
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return PAGE_COUNT
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    PAGE_INFO-> GeneralDetailFragment()
                    PAGE_VIDEOS -> TrailersFragment()
                    PAGE_REVIEW -> ReviewsFragment()
                    PAGE_CAST->CastFragment()
                    else -> throw IndexOutOfBoundsException()
                }
            }

        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            PAGE_INFO->"Info"
            PAGE_VIDEOS -> "Trailers"
            PAGE_REVIEW -> "Reviews"
            PAGE_CAST->"Casts"
            else -> null
        }
    }

    fun getDetails(id: Long?) {
        id?.let {
            viewModel.getDetails(id)
        }
    }
}

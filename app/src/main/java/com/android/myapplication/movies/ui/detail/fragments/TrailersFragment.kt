package com.android.myapplication.movies.ui.detail.fragments


import YOUTUBE_BASE_URL
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.myapplication.movies.databinding.FragmentTrailerBinding
import com.android.myapplication.popularmovies.api.model.Movie
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
private const val MOVIE_ID_EXTRA = "movie_id_extra"

class TrailersFragment : Fragment() {

    private lateinit var binding: FragmentTrailerBinding
    private val viewModel: DetailFragmentViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrailerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        val recyclerView = binding.listVideos
        recyclerView.apply {
            adapter = TrailerAdapter { video ->
                val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${video.key}"))
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
    }

}

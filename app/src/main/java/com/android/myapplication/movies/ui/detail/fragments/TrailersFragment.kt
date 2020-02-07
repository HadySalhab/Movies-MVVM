package com.android.myapplication.movies.ui.detail.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.FragmentReviewsBinding
import com.android.myapplication.movies.databinding.FragmentTrailerBinding
import com.android.myapplication.movies.ui.detail.DetailActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class TrailersFragment : Fragment() {
    private lateinit var binding: FragmentTrailerBinding
    private val viewModel: DetailActivityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrailerBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        initRecyclerView()
        return binding.root
    }
    private fun initRecyclerView(){
        val recyclerView = binding.listVideos
        recyclerView.apply {
            adapter = VideoAdapter()
        }
    }

}

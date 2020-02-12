package com.android.myapplication.movies.ui.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.movies.databinding.FragmentCastBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val MOVIE_ID_EXTRA = "movie_id_extra"
class CastFragment : Fragment() {

    private lateinit var binding: FragmentCastBinding
    private val viewModel: DetailFragmentViewModel by sharedViewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCastBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        binding.viewModel = viewModel
        return binding.root
    }

    private fun initRecyclerView() {
        val recyclerView = binding.listCasts
        recyclerView.apply {
            adapter = CastAdapter()
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}
package com.android.myapplication.movies.ui.detail.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.myapplication.movies.databinding.FragmentCastBinding
import com.android.myapplication.movies.databinding.FragmentTrailerBinding
import com.android.myapplication.movies.util.RecyclerViewDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class CastFragment : Fragment() {
    companion object {
        private const val TAG = "TrailersFragment"
    }

    private lateinit var binding: FragmentCastBinding
    private val viewModel: DetailFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCastBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
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
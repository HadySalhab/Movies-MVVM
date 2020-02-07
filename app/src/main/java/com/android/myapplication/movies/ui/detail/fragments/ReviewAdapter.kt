package com.android.myapplication.movies.ui.detail.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.android.myapplication.movies.R
import com.android.myapplication.movies.databinding.ItemReviewBinding
import com.android.myapplication.popularmovies.api.model.Review

class ReviewAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Review>() {

        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
           return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ReviewViewHolder.getInstance(parent)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReviewViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Review>) {
        differ.submitList(list)
    }

    class ReviewViewHolder private constructor(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): ReviewViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemReviewBinding.inflate(inflater,parent,false)
                return ReviewViewHolder(binding)
            }
        }

        fun bind(review: Review) {
            binding.reviews = review
        }
    }
}
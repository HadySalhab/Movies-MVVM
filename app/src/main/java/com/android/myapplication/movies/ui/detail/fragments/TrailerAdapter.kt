package com.android.myapplication.movies.ui.detail.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.myapplication.movies.databinding.ItemTrailerBinding
import com.android.myapplication.popularmovies.api.model.Video

class TrailerAdapter(private val onVideoClicked:(Video)->Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Video>() {

        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
           return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return VideoViewHolder.getInstance(parent,onVideoClicked)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Video>) {
        differ.submitList(list)
    }

    class VideoViewHolder private constructor(private val binding:ItemTrailerBinding,val onVideoClicked:(Video)->Unit) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup,onVideoClicked:(Video)->Unit): VideoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemTrailerBinding.inflate(inflater,parent,false)
                return VideoViewHolder(binding,onVideoClicked)
            }
        }

        fun bind(review: Video) {
            binding.video = review
            binding.viewHolder = this
        }
    }
}
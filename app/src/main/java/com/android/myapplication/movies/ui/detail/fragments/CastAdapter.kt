package com.android.myapplication.movies.ui.detail.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.android.myapplication.movies.databinding.ItemCastBinding
import com.android.myapplication.popularmovies.api.model.Cast

class CastAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {

        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.castId == oldItem.castId
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CastViewHolder.getInstance(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CastViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Cast>) {
        differ.submitList(list)
    }

    class CastViewHolder private constructor(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun getInstance(parent: ViewGroup): CastViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCastBinding.inflate(inflater,parent,false)
                return CastViewHolder(
                    binding
                )
            }
        }

        fun bind(cast: Cast) {
            binding.cast = cast
        }
    }

}
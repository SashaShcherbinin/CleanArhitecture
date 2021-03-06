package com.movies.popular.presentation.feature.search.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.movies.popular.domain.model.Movie
import com.movies.popular.presentation.search.R
import com.movies.popular.presentation.search.databinding.ItemSearchBinding

class SearchAdapter(private val viewModel: SearchViewModel)
    : ListAdapter<Movie, SearchAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(DataBindingUtil
                .inflate(LayoutInflater.from(parent.context),
                        R.layout.item_search, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemSearchBinding)
        : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?) {
            binding.item = item
            binding.viewModel = viewModel
        }
    }

    companion object {

        private val DIFF_CALLBACK = object
            : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldConcert: Movie, newConcert: Movie): Boolean {
                return oldConcert.id == newConcert.id
            }

            override fun areContentsTheSame(oldConcert: Movie,
                                            newConcert: Movie): Boolean {
                return oldConcert == newConcert
            }
        }
    }

}

package com.movies.popular.feature.movie

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.movies.popular.creator.MovieScreenCreator

class MovieScreenCreatorImpl : MovieScreenCreator {

    override fun getMovieIntent(context: Context, movieId: Int): Intent {
        return MovieDetailActivity.getIntent(context, movieId)
    }

    override fun getMovieFragment(movieId: Int): Fragment {
        return DetailMovieFragment.newInstance(movieId)
    }
}
package com.movies.popular.app.generator

import android.content.Context
import android.content.Intent
import com.movies.popular.domain.IntentGenerator
import com.movies.popular.feature.movie.MovieDetailActivity
import com.movies.popular.presentation.feature.search.SearchActivity

class IntentGeneratorImpl constructor(private val context: Context) : IntentGenerator {

    override fun getSearchActivityIntent(): Intent {
        return SearchActivity.getIntent(context)
    }

    override fun getMovieDetailActivityIntent(id: Int): Intent {
        return MovieDetailActivity.getIntent(context, id)
    }
}
package com.movies.popular.app.generator

import androidx.fragment.app.Fragment
import com.movies.popular.domain.FragmentGenerator
import com.movies.popular.feature.movie.DetailMovieFragment

class FragmentGeneratorImpl : FragmentGenerator {

    override fun getMovieFragment(movieId: Int): Fragment {
        return DetailMovieFragment.newInstance(movieId)
    }
}
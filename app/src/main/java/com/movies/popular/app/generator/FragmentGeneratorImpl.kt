package com.movies.popular.app.generator

import androidx.fragment.app.Fragment
import com.movies.popular.creator.MovieScreenCreator
import com.movies.popular.domain.FragmentGenerator

class FragmentGeneratorImpl : FragmentGenerator {

    override fun getMovieFragment(movieId: Int): Fragment {
        val creator = Class
                .forName("com.movies.popular.feature.movie.MovieScreenCreatorImpl")
                .newInstance() as MovieScreenCreator
        return creator.getMovieFragment(movieId)
    }
}
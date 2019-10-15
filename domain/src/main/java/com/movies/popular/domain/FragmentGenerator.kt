package com.movies.popular.domain

import androidx.fragment.app.Fragment

interface FragmentGenerator {

    fun getMovieFragment(movieId: Int): Fragment

}
package com.movies.popular.domain

import com.movies.popular.domain.repository.MovieRepository
import com.movies.popular.domain.repository.SearchRepository

interface RepositoryProvider {
    fun getMovieRepository(): MovieRepository
    fun getSearchRepository(): SearchRepository
}
package com.movies.popular.domain.repository

import com.movies.popular.domain.model.Movie
import io.reactivex.Observable

interface SearchRepository {
    fun getSearchMovieList(query: String): Observable<List<Movie>>
}
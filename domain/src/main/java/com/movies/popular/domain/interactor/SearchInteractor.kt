package com.movies.popular.domain.interactor

import com.movies.popular.domain.model.Movie
import com.movies.popular.domain.repository.SearchRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchInteractor
@Inject
constructor(private val searchRepository: SearchRepository) {

    fun getSearchMovieList(query: String): Observable<List<Movie>> {
        return searchRepository.getSearchMovieList(query)
    }
}
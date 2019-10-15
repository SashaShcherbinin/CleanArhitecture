package com.movies.popular.domain.interactor

import com.movies.popular.domain.model.Movie
import com.movies.popular.domain.model.MovieDetail
import com.movies.popular.domain.model.MovieFilter
import com.movies.popular.domain.model.PageBundle
import com.movies.popular.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class MovieInteractor
@Inject
constructor(private val movieRepository: MovieRepository) {

    fun getMovieList(): Observable<PageBundle<Movie>> {
        return movieRepository.getMovieList()
    }

    fun fetchNextMovieList(): Completable {
        return movieRepository.fetchNextMovieList()
    }

    fun refresh(): Completable {
        return movieRepository.refresh()
    }

    fun getFilter(): Observable<MovieFilter> {
        return movieRepository.getFilter()
    }

    fun setFilter(moveFilter: MovieFilter): Completable {
        return movieRepository.setFilter(moveFilter)
    }

    fun observeMovie(id: Int): Observable<MovieDetail> {
        return movieRepository.observeMovie(id)
    }

    fun refreshMovie(id: Int): Completable {
        return movieRepository.refreshMovie(id)
    }

}

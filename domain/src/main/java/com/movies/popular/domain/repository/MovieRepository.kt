package com.movies.popular.domain.repository

import com.movies.popular.domain.model.Movie
import com.movies.popular.domain.model.MovieDetail
import com.movies.popular.domain.model.MovieFilter
import com.movies.popular.domain.model.PageBundle
import io.reactivex.Completable
import io.reactivex.Observable

interface MovieRepository {
    fun getMovieList(): Observable<PageBundle<Movie>>
    fun fetchNextMovieList(): Completable
    fun refresh(): Completable
    fun observeMovie(id: Int): Observable<MovieDetail>
    fun refreshMovie(id: Int): Completable
    fun getFilter(): Observable<MovieFilter>
    fun setFilter(moveFilter: MovieFilter) : Completable
}
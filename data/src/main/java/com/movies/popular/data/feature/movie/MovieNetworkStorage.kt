package com.movies.popular.data.feature.movie

import com.movies.popular.data.common.mappers.Mappers
import com.movies.popular.data.network.mapper.MovieDetailResponseMapper
import com.movies.popular.data.network.mapper.MovieResponseMapper
import com.movies.popular.data.network.service.DiscoverService
import com.movies.popular.domain.model.Movie
import com.movies.popular.domain.model.MovieDetail
import io.reactivex.Single
import javax.inject.Inject

class MovieNetworkStorage
@Inject
constructor(private val discoverService: DiscoverService,
            private val movieResponseMapper: MovieResponseMapper,
            private val movieDetailResponseMapper: MovieDetailResponseMapper) {

    fun getMovies(page: Int): Single<List<Movie>> {
        return discoverService.getMovies(page = page)
                .map { Mappers.mapCollection(it.results, movieResponseMapper) }
    }

    fun getMovie(id: Int): Single<MovieDetail> {
        return discoverService.getMovie(id)
                .map { movieDetailResponseMapper.map(it) }
    }
}
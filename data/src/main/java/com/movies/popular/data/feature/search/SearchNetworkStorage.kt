package com.movies.popular.data.feature.search

import com.movies.popular.data.common.mappers.Mappers
import com.movies.popular.data.network.mapper.MovieResponseMapper
import com.movies.popular.data.network.service.SearchService
import com.movies.popular.domain.model.Movie
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class SearchNetworkStorage
@Inject
constructor(private val searchService: SearchService,
            private val movieResponseMapper: MovieResponseMapper) {

    fun getMovies(query: String): Single<List<Movie>> {
        return if (query.isEmpty()) {
            Single.just(Collections.emptyList())
        } else {
            searchService.getMoviesFound(query)
                    .map { Mappers.mapCollection(it.results, movieResponseMapper) }
        }
    }
}
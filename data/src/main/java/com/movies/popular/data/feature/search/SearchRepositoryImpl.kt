package com.movies.popular.data.feature.search

import com.movies.popular.data.common.MemoryListStorage
import com.movies.popular.data.common.cashe.CachePolicy
import com.movies.popular.data.di.scope.DataScope
import com.movies.popular.domain.model.Movie
import com.movies.popular.domain.repository.SearchRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@DataScope
class SearchRepositoryImpl
@Inject
constructor(private val searchNetworkStorage: SearchNetworkStorage) : SearchRepository {

    private val movieListStorage = MemoryListStorage
            .Builder<String, Movie>()
            .fetcher { searchNetworkStorage.getMovies(it) }
            .cachePolicy(CachePolicy.create(10, TimeUnit.MINUTES))
            .capacity(10)
            .build()

    override fun getSearchMovieList(query: String): Observable<List<Movie>> {
        return movieListStorage[query]
    }
}
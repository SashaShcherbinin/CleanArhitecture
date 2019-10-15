package com.movies.popular.data.feature.movie

import com.movies.popular.data.common.MemoryPagedListStorage
import com.movies.popular.data.common.MemoryStorage
import com.movies.popular.data.common.cashe.CachePolicy
import com.movies.popular.data.di.scope.DataScope
import com.movies.popular.domain.model.Movie
import com.movies.popular.domain.model.MovieDetail
import com.movies.popular.domain.model.MovieFilter
import com.movies.popular.domain.model.PageBundle
import com.movies.popular.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@DataScope
class MovieRepositoryImpl
@Inject
constructor(private val movieNetworkStorage: MovieNetworkStorage,
            private val filterPreference: MovieFilterPreference)
    : MovieRepository {

    private val movieListStorage = MemoryPagedListStorage
            .Builder<Unit, Movie> { params ->
                movieNetworkStorage.getMovies(params.page)
                        .map { MemoryPagedListStorage.FetchResult(it, it.isNotEmpty()) }
            }
            .cachePolicy(CachePolicy.create(1, TimeUnit.MINUTES))
            .capacity(1)
            .build()

    private val movieStorage = MemoryStorage
            .Builder<Int, MovieDetail>()
            .fetcher { movieNetworkStorage.getMovie(it) }
            .cachePolicy(CachePolicy.create(1, TimeUnit.MINUTES))
            .capacity(1)
            .build()

    override fun getMovieList(): Observable<PageBundle<Movie>> {
        return movieListStorage[Unit]
    }

    override fun fetchNextMovieList(): Completable {
        return movieListStorage.fetchNext(Unit)
    }

    override fun getFilter(): Observable<MovieFilter> {
        return filterPreference.getFilter()
    }

    override fun setFilter(moveFilter: MovieFilter): Completable {
        return filterPreference.saveFilter(moveFilter)
    }

    override fun refresh(): Completable {
        return movieListStorage.refresh(Unit)
    }

    override fun observeMovie(id: Int): Observable<MovieDetail> {
        return movieStorage[id]
    }

    override fun refreshMovie(id: Int): Completable {
        return movieStorage.refresh(id)
    }
}

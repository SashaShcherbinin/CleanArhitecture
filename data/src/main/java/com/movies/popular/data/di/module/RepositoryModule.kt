package com.movies.popular.data.di.module

import com.movies.popular.data.feature.movie.MovieRepositoryImpl
import com.movies.popular.data.feature.search.SearchRepositoryImpl
import com.movies.popular.domain.repository.MovieRepository
import com.movies.popular.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun getMovieRepository(repository: MovieRepositoryImpl): MovieRepository = repository

    @Provides
    fun getSearchRepository(repository: SearchRepositoryImpl): SearchRepository = repository

}

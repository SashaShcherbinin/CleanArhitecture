package com.movies.popular.di.module

import com.movies.popular.domain.RepositoryProvider
import com.movies.popular.domain.repository.MovieRepository
import com.movies.popular.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule(private val provider: RepositoryProvider) : RepositoryProvider {

    @Provides
    override fun getSearchRepository(): SearchRepository = provider.getSearchRepository()

    @Provides
    override fun getMovieRepository(): MovieRepository = provider.getMovieRepository()

}
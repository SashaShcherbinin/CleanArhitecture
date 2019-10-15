package com.movies.popular.data.di.module

import com.movies.popular.data.di.qualifier.QueryQualifier
import com.movies.popular.data.network.service.DiscoverService
import com.movies.popular.data.network.service.SearchService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkServiceModule {

    @Provides
    internal fun provideDiscoverService(@QueryQualifier
                                        retrofit: Retrofit)
            : DiscoverService {
        return retrofit.create(DiscoverService::class.java)
    }

    @Provides
    internal fun provideSearchService(@QueryQualifier
                                      retrofit: Retrofit)
            : SearchService {
        return retrofit.create(SearchService::class.java)
    }

}

package com.movies.popular.di.module

import com.movies.popular.common.error.DefaultErrorHandler
import com.movies.popular.common.error.ErrorHandler
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideDefaultError(errorHandler: DefaultErrorHandler)
            : ErrorHandler = errorHandler
}

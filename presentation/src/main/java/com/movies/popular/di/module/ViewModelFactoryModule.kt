package com.movies.popular.di.module

import androidx.lifecycle.ViewModelProvider
import com.movies.popular.common.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory)
            : ViewModelProvider.Factory
}
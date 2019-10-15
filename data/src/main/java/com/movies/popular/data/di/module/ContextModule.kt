package com.movies.popular.data.di.module

import android.content.Context

import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    internal fun provideContext(): Context = context

}

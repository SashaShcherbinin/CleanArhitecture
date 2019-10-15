package com.movies.popular.di.module

import android.content.Context
import com.movies.popular.domain.FragmentGenerator
import com.movies.popular.domain.IntentGenerator

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context,
                private val intentGenerator: IntentGenerator,
                private val fragmentGenerator: FragmentGenerator) {

    @Provides
    internal fun provideContext(): Context = context

    @Provides
    internal fun provideIntentGenerator(): IntentGenerator = intentGenerator

    @Provides
    internal fun provideFragmentGenerator(): FragmentGenerator = fragmentGenerator

}

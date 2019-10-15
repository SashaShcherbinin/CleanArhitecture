package com.movies.popular.di

import android.content.Context
import com.movies.popular.App
import com.movies.popular.common.error.ErrorHandler
import com.movies.popular.di.module.AppModule
import com.movies.popular.di.module.PresentationModule
import com.movies.popular.di.module.RepositoryModule
import com.movies.popular.di.scope.PresentationScope
import com.movies.popular.domain.FragmentGenerator
import com.movies.popular.domain.IntentGenerator
import com.movies.popular.domain.RepositoryProvider
import dagger.Component

@Component(
        modules = [
            AppModule::class,
            RepositoryModule::class,
            PresentationModule::class
        ])
@PresentationScope
interface PresentationComponent : RepositoryProvider {

    fun inject(app: App)

    fun getContext(): Context
    fun getErrorHandler(): ErrorHandler
    fun getIntentGenerator(): IntentGenerator
    fun getFragmentGenerator(): FragmentGenerator

    class Initializer private constructor() {
        companion object {

            fun init(app: App,
                     intentGenerator: IntentGenerator,
                     fragmentGenerator: FragmentGenerator,
                     repositoryProvider: RepositoryProvider): PresentationComponent {

                return DaggerPresentationComponent.builder()
                        .repositoryModule(RepositoryModule(repositoryProvider))
                        .appModule(AppModule(app, intentGenerator, fragmentGenerator))
                        .build()
            }
        }
    }
}

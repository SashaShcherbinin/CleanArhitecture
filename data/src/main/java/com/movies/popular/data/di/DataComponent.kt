package com.movies.popular.data.di

import android.content.Context
import com.movies.popular.data.di.module.ContextModule
import com.movies.popular.data.di.module.NetworkModule
import com.movies.popular.data.di.module.NetworkServiceModule
import com.movies.popular.data.di.module.RepositoryModule
import com.movies.popular.data.di.scope.DataScope
import com.movies.popular.domain.RepositoryProvider
import dagger.Component

@DataScope
@Component(
        modules = [
            ContextModule::class,
            NetworkModule::class,
            NetworkServiceModule::class,
            RepositoryModule::class
        ])
interface DataComponent : RepositoryProvider {

    class Initializer private constructor() {
        companion object {

            fun init(context: Context): DataComponent {
                return DaggerDataComponent.builder()
                        .contextModule(ContextModule(context))
                        .build()
            }
        }
    }
}
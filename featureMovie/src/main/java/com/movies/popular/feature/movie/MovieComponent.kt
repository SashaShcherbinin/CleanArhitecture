package com.movies.popular.feature.movie

import androidx.lifecycle.ViewModel
import com.movies.popular.App
import com.movies.popular.di.PresentationComponent
import com.movies.popular.di.ViewModelKey
import com.movies.popular.di.module.ViewModelFactoryModule
import com.movies.popular.di.scope.ModuleScope
import com.movies.popular.domain.utils.LazyWeakReference
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@ModuleScope
@Component(
        dependencies = [PresentationComponent::class],
        modules = [
            ViewModelFactoryModule::class,
            MovieComponent.MainViewModelModule::class
        ]
)
interface MovieComponent {

    companion object {
        private val weekReference = LazyWeakReference {
            DaggerMovieComponent.builder()
                    .presentationComponent(App.getComponent())
                    .build()
        }

        fun getInstance(): MovieComponent = weekReference.get()
    }

    fun inject(baseFragment: DetailMovieFragment)
    fun inject(movieDetailActivity: MovieDetailActivity)

    @Module
    abstract class MainViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(DetailMovieViewModel::class)
        abstract fun bindDetailViewModel(viewModel: DetailMovieViewModel): ViewModel

    }

}

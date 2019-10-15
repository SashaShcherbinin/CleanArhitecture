package com.movies.popular.feature

import androidx.lifecycle.ViewModel
import com.movies.popular.di.PresentationComponent
import com.movies.popular.di.ViewModelKey
import com.movies.popular.di.module.ViewModelFactoryModule
import com.movies.popular.di.scope.ModuleScope
import com.movies.popular.domain.utils.LazyWeakReference
import com.movies.popular.feature.filter.FilterActivity
import com.movies.popular.feature.filter.fragment.FilterFragment
import com.movies.popular.feature.filter.fragment.FilterViewModel
import com.movies.popular.feature.main.MainActivity
import com.movies.popular.feature.main.discover.DiscoverFragment
import com.movies.popular.feature.main.discover.DiscoverViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@ModuleScope
@Component(
        dependencies = [PresentationComponent::class],
        modules = [
            ViewModelFactoryModule::class,
            BaseComponent.MainViewModelModule::class
        ]
)
interface BaseComponent {

    companion object {
        private val weekReference = LazyWeakReference {
            DaggerBaseComponent.builder()
                    .presentationComponent(com.movies.popular.App.getComponent())
                    .build()
        }

        fun getInstance(): BaseComponent = weekReference.get()
    }

    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: DiscoverFragment)
    fun inject(filterActivity: FilterActivity)
    fun inject(filterActivity: FilterFragment)

    @Module
    abstract class MainViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(DiscoverViewModel::class)
        abstract fun bindMoviesViewModel(viewModel: DiscoverViewModel): ViewModel

        @Binds
        @IntoMap
        @ViewModelKey(FilterViewModel::class)
        abstract fun bindFilterViewModel(viewModel: FilterViewModel): ViewModel

    }

}

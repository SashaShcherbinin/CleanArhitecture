package com.movies.popular.presentation.feature.search

import androidx.lifecycle.ViewModel
import com.movies.popular.App
import com.movies.popular.di.PresentationComponent
import com.movies.popular.di.ViewModelKey
import com.movies.popular.di.module.ViewModelFactoryModule
import com.movies.popular.di.scope.ModuleScope
import com.movies.popular.domain.utils.LazyWeakReference
import com.movies.popular.presentation.feature.search.fragment.SearchFragment
import com.movies.popular.presentation.feature.search.fragment.SearchViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntoMap

@ModuleScope
@Component(
        dependencies = [PresentationComponent::class],
        modules = [
            ViewModelFactoryModule::class,
            SearchComponent.SearchViewModelModule::class
        ]
)
interface SearchComponent {

    companion object {
        private val weekReference = LazyWeakReference {
            DaggerSearchComponent.builder()
                    .presentationComponent(App.getComponent())
                    .build()
        }

        fun getInstance(): SearchComponent = weekReference.get()
    }

    fun inject(activity: SearchActivity)
    fun inject(fragment: SearchFragment)

    @Module
    abstract class SearchViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(SearchViewModel::class)
        abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    }

}
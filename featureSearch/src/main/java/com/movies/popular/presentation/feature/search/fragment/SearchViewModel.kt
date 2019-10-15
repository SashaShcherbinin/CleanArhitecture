package com.movies.popular.presentation.feature.search.fragment

import androidx.lifecycle.MutableLiveData
import com.movies.popular.common.BaseViewModel
import com.movies.popular.common.error.ErrorHandler
import com.movies.popular.common.livedata.DebounceMutableLiveData
import com.movies.popular.common.livedata.SingleLiveEvent
import com.movies.popular.domain.interactor.SearchInteractor
import com.movies.popular.domain.model.Movie
import com.movies.popular.type.ContentState
import com.movies.popular.utils.RxDisposable
import com.movies.popular.utils.extensions.defaultSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class SearchViewModel
@Inject
constructor(
        private val searchInteractor: SearchInteractor,
        private val errorHandler: ErrorHandler)
    : BaseViewModel() {

    var content = MutableLiveData<List<Movie>>()
    var contentState = MutableLiveData<ContentState>()
    var hasNext = MutableLiveData<Boolean?>()
    var searchText = DebounceMutableLiveData<String>(1000)
    var itemClickedEvent = SingleLiveEvent<Int>()

    init {
        contentState.value = ContentState.EMPTY
        searchText.observeForever {
            observeSearchMovies(it)
        }
    }

    private fun observeSearchMovies(query: String) {
        RxDisposable.manage(this, "searchMovies",
                searchInteractor.getSearchMovieList(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { contentState.value = ContentState.LOADING }
                        .defaultSubscribe(errorHandler, errorMessage) {
                            if (it.isEmpty()) {
                                contentState.value = ContentState.EMPTY
                            } else {
                                contentState.value = ContentState.CONTENT
                            }
                            content.value = it
                        })
    }


    @Suppress("UNUSED_PARAMETER")
    fun onItemClicked(movie: Movie) {
        itemClickedEvent.value = movie.id
    }


}
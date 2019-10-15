package com.movies.popular.feature.main.discover

import androidx.lifecycle.MutableLiveData
import com.movies.popular.common.BaseViewModel
import com.movies.popular.common.error.ErrorHandler
import com.movies.popular.common.livedata.SingleLiveEvent
import com.movies.popular.domain.interactor.MovieInteractor
import com.movies.popular.domain.model.Movie
import com.movies.popular.type.ContentState
import com.movies.popular.utils.RxDisposable
import com.movies.popular.utils.extensions.defaultSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DiscoverViewModel
@Inject
constructor(private val movieInteractor: MovieInteractor,
            private val errorHandler: ErrorHandler)
    : BaseViewModel() {

    var contentState = MutableLiveData<ContentState>()
    var content = MutableLiveData<List<Movie>>()
    var refreshing = MutableLiveData<Boolean>()
    var hasNext = MutableLiveData<Boolean?>()
    var loading = MutableLiveData<Boolean>()

    var itemClickedEvent = SingleLiveEvent<Int>()

    init {
        observeMovies()
    }

    private fun observeMovies() {
        RxDisposable.manage(this, "movies",
                movieInteractor.getMovieList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { contentState.setValue(ContentState.CONTENT) }
                        .doOnError { contentState.setValue(ContentState.ERROR) }
                        .doOnNext {
                            if (it.data.isEmpty()) contentState.value = ContentState.EMPTY
                            else contentState.value = ContentState.CONTENT
                        }
                        .defaultSubscribe(errorHandler, errorMessage) {
                            content.value = it.data
                            hasNext.value = it.hasNext
                        }
        )
    }

    fun onRetry() {
        observeMovies()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onItemClicked(movie: Movie) {
        itemClickedEvent.value = movie.id
    }

    fun onRefresh() {
        RxDisposable.manage(this, "refresh",
                movieInteractor.refresh()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { refreshing.value = true }
                        .defaultSubscribe(errorHandler, errorMessage) {
                            refreshing.value = false
                        })
    }

    fun fetchNext() {
        RxDisposable.manage(this, "fetchNext",
                movieInteractor.fetchNextMovieList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { loading.value = true }
                        .doOnTerminate { loading.value = false }
                        .defaultSubscribe(errorHandler, errorMessage))
    }
}
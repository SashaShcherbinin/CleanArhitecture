package com.movies.popular.feature.movie

import androidx.lifecycle.MutableLiveData
import com.movies.popular.common.BaseViewModel
import com.movies.popular.common.OneTimeAction
import com.movies.popular.common.error.ErrorHandler
import com.movies.popular.domain.interactor.MovieInteractor
import com.movies.popular.type.ContentState
import com.movies.popular.utils.RxDisposable
import com.movies.popular.utils.extensions.defaultSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailMovieViewModel
@Inject
constructor(private val movieInteractor: MovieInteractor,
            private val errorHandler: ErrorHandler)
    : BaseViewModel() {

    var movieId = MutableLiveData<Int>()

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    var contentState = MutableLiveData<ContentState>()
    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var imageUri = MutableLiveData<String>()
    var refreshing = MutableLiveData<Boolean>()
    var releaseDate = MutableLiveData<String>()

    private val prepareAction = OneTimeAction<Int> { id ->
        RxDisposable.manage(this, "movieDetails",
                movieInteractor.observeMovie(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { contentState.value = ContentState.LOADING }
                        .doOnNext { contentState.value = ContentState.CONTENT }
                        .defaultSubscribe(errorHandler, errorMessage) {
                            title.value = it.title
                            description.value = it.overview
                            imageUri.value = it.posterImage
                            releaseDate.value = convertLongToString(it.releaseDate!!)
                        }
        )
    }

    init {
        movieId.observeForever {
            prepareAction.invoke(it)
        }
    }

    fun onRetry() {
        prepareAction.reset()
        prepareAction.invoke(movieId.value!!)
    }

    fun onRefresh() {
        RxDisposable.manage(this, "refresh",
                movieInteractor.refreshMovie(movieId.value!!)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { refreshing.value = true }
                        .doOnTerminate { refreshing.value = false }
                        .defaultSubscribe(errorHandler, errorMessage)
        )
    }

    private fun convertLongToString(releaseDate: Long): String {
        return simpleDateFormat.format(Date(releaseDate))
    }
}
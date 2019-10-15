package com.movies.popular.feature.filter.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.movies.popular.common.BaseViewModel
import com.movies.popular.common.error.ErrorHandler
import com.movies.popular.domain.interactor.MovieInteractor
import com.movies.popular.domain.model.MovieFilter
import com.movies.popular.domain.type.SortBy
import com.movies.popular.feature.R
import com.movies.popular.utils.RxDisposable
import com.movies.popular.utils.extensions.defaultSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class FilterViewModel
@Inject
constructor(private val movieInteractor: MovieInteractor,
            private val errorHandler: ErrorHandler,
            private val context: Context)
    : BaseViewModel() {

    val array = MutableLiveData<Array<String>>()
    val position = MutableLiveData<Int>()

    init {
        array.value = sortTypeArray()
        observeFilter()
        position.observeForever {
            updatePosition(it)
        }
    }

    private fun updatePosition(position: Int) {
        val moveFilter = MovieFilter(convertPositionToEnum(position))
        RxDisposable.manage(this, movieInteractor.setFilter(moveFilter)
                .subscribeOn(AndroidSchedulers.mainThread())
                .defaultSubscribe(errorHandler, errorMessage))
    }

    private fun observeFilter() {
        RxDisposable.manage(this, movieInteractor.getFilter()
                .take(1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .defaultSubscribe(errorHandler, errorMessage) {
                    position.value = convertEnumToPosition(it.sortBy)
                })
    }

    private fun sortTypeArray(): Array<String> {
        return arrayOf(
                context.getString(R.string.filter_sort_type_popularity_asc),
                context.getString(R.string.filter_sort_type_popularity_desc),
                context.getString(R.string.filter_sort_type_release_date_asc),
                context.getString(R.string.filter_sort_type_release_date_desc),
                context.getString(R.string.filter_sort_type_revenue_asc),
                context.getString(R.string.filter_sort_type_revenue_desc),
                context.getString(R.string.filter_sort_type_primary_release_date_asc),
                context.getString(R.string.filter_sort_type_primary_release_date_desc),
                context.getString(R.string.filter_sort_type_original_title_asc),
                context.getString(R.string.filter_sort_type_original_title_desc),
                context.getString(R.string.filter_sort_type_vote_average_asc),
                context.getString(R.string.filter_sort_type_vote_average_desc),
                context.getString(R.string.filter_sort_type_vote_count_asc),
                context.getString(R.string.filter_sort_type_vote_count_desc)
        )
    }

    private fun convertPositionToEnum(position: Int): SortBy {
        return when (position) {
            0 -> SortBy.POPULARITY_ASC
            1 -> SortBy.POPULARITY_DESC
            2 -> SortBy.RELEASE_DATE_ASC
            3 -> SortBy.RELEASE_DATE_DESC
            4 -> SortBy.REVENUE_ASC
            5 -> SortBy.REVENUE_DESC
            6 -> SortBy.PRIMARY_RELEASE_DATE_ASC
            7 -> SortBy.PRIMARY_RELEASE_DATE_DESC
            8 -> SortBy.ORIGINAL_TITLE_ASC
            9 -> SortBy.ORIGINAL_TITLE_DESC
            10 -> SortBy.VOTE_AVERAGE_ASC
            11 -> SortBy.VOTE_AVERAGE_DESC
            12 -> SortBy.VOTE_COUNT_ASC
            13 -> SortBy.VOTE_COUNT_DESC
            else -> error("Invalid position $position")
        }
    }

    private fun convertEnumToPosition(sortBy: SortBy): Int {
        return when (sortBy) {
            SortBy.POPULARITY_ASC -> 0
            SortBy.POPULARITY_DESC -> 1
            SortBy.RELEASE_DATE_ASC -> 2
            SortBy.RELEASE_DATE_DESC -> 3
            SortBy.REVENUE_ASC -> 4
            SortBy.REVENUE_DESC -> 5
            SortBy.PRIMARY_RELEASE_DATE_ASC -> 6
            SortBy.PRIMARY_RELEASE_DATE_DESC -> 7
            SortBy.ORIGINAL_TITLE_ASC -> 8
            SortBy.ORIGINAL_TITLE_DESC -> 9
            SortBy.VOTE_AVERAGE_ASC -> 10
            SortBy.VOTE_AVERAGE_DESC -> 11
            SortBy.VOTE_COUNT_ASC -> 12
            SortBy.VOTE_COUNT_DESC -> 13
            else -> error("Invalid enum $position")
        }
    }
}
package com.movies.popular.data.feature.movie

import android.content.Context
import com.movies.popular.data.Preference
import com.movies.popular.data.common.PreferenceStorage
import com.movies.popular.domain.model.MovieFilter
import com.movies.popular.domain.type.SortBy
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class MovieFilterPreference
@Inject
constructor(context: Context) {
    private val preferenceStorage: PreferenceStorage =
            PreferenceStorage(context, Preference.MovieFilter.PREFERENCE_NAME)

    fun saveFilter(movieFilter: MovieFilter): Completable {
        return preferenceStorage.update(Preference.MovieFilter.PREFERENCE_NAME) {
            it.putString(Preference.MovieFilter.FIELD_SORT_BY, movieFilter.sortBy.name)
        }
    }

    fun getFilter(): Observable<MovieFilter> {
        return preferenceStorage.get(Preference.MovieFilter.PREFERENCE_NAME) {
            val value = it.getString(Preference.MovieFilter.FIELD_SORT_BY, null)
            if (value != null) {
                MovieFilter(SortBy.valueOf(value))
            } else {
                MovieFilter(SortBy.ORIGINAL_TITLE_ASC)
            }
        }
    }
}

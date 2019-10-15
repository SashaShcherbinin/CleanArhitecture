package com.movies.popular.data.network.mapper

import com.movies.popular.data.common.mappers.Mapper
import com.movies.popular.domain.type.SortBy
import javax.inject.Inject

class FilterResponseMapper
@Inject
constructor()
    : Mapper<SortBy, String> {

    override fun map(value: SortBy): String {
        when (value) {
            SortBy.POPULARITY_ASC -> return "popularity.asc"
            SortBy.POPULARITY_DESC -> return "popularity.desc"
            SortBy.RELEASE_DATE_ASC -> return "release_date.asc"
            SortBy.RELEASE_DATE_DESC -> return "release_date.desc"
            SortBy.REVENUE_ASC -> return "revenue.asc"
            SortBy.REVENUE_DESC -> return "revenue.desc"
            SortBy.PRIMARY_RELEASE_DATE_ASC -> return "primary_release_date.asc"
            SortBy.PRIMARY_RELEASE_DATE_DESC -> return "primary_release_date.desc"
            SortBy.ORIGINAL_TITLE_ASC -> return "original_title.asc"
            SortBy.ORIGINAL_TITLE_DESC -> return "original_title.desc"
            SortBy.VOTE_AVERAGE_ASC -> return "vote_average.asc"
            SortBy.VOTE_AVERAGE_DESC -> return "vote_average.desc"
            SortBy.VOTE_COUNT_ASC -> return "vote_count.asc"
            SortBy.VOTE_COUNT_DESC -> return "vote_count.desc"
        }
    }
}
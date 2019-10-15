package com.movies.popular.data.network.mapper

import com.movies.popular.data.common.mappers.Mapper
import com.movies.popular.data.network.model.MovieDetailDto
import com.movies.popular.domain.model.MovieDetail
import java.util.*

import javax.inject.Inject

import java.text.SimpleDateFormat

class MovieDetailResponseMapper
@Inject
constructor()
    : Mapper<MovieDetailDto, MovieDetail> {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    override fun map(value: MovieDetailDto): MovieDetail {
        return MovieDetail(
                value.id!!,
                value.overview,
                value.title,
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + value.backdropPath,
                convertStringToLong(value.releaseDate!!)
        )
    }

    private fun convertStringToLong(releaseDate: String): Long {
        return simpleDateFormat.parse(releaseDate).time
    }
}

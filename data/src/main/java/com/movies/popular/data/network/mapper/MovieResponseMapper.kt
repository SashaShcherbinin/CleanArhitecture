package com.movies.popular.data.network.mapper

import com.movies.popular.data.common.mappers.Mapper
import com.movies.popular.data.network.model.MovieDto
import com.movies.popular.domain.model.Movie
import javax.inject.Inject

class MovieResponseMapper
@Inject
constructor()
    : Mapper<MovieDto, Movie> {

    override fun map(value: MovieDto): Movie {
        return Movie(
                value.id!!,
                value.title,
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + value.backdropPath
        )
    }

}

package com.movies.popular.domain.model

data class MovieDetail(
        val id: Int,
        val overview: String?,
        val title: String?,
        val posterImage: String?,
        val releaseDate: Long?
)
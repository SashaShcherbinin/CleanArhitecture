package com.movies.popular.data.network.mapper

import com.google.gson.Gson
import com.movies.popular.data.common.JsonFileHelper
import com.movies.popular.data.network.model.MovieDetailDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailResponseMapperTest {

    @Test
    fun map() {
        val movieDetailResponseMapper = MovieDetailResponseMapper()
        val jsonContents = JsonFileHelper()
                .getJsonContents("response_movie_detail.json")

        val movieDetailDto = Gson()
                .fromJson(jsonContents, MovieDetailDto::class.java)

        val movieDetail = movieDetailResponseMapper.map(movieDetailDto)

        assertEquals(11559, movieDetail.id)
        assertEquals(1126213200000, movieDetail.releaseDate) // "2005-09-09"
        assertEquals("Because of the actions of her irresponsible parents, a young " +
                "girl is left alone on a decrepit country estate and survives inside her " +
                "fantastic imagination.", movieDetail.overview)
        assertEquals("https://image.tmdb.org/t/p/w600_and_h900_bestv2//" +
                "273sZucrvytkuYjsYYSa7RtQa2B.jpg", movieDetail.posterImage)
        assertEquals("Tideland", movieDetail.title)
    }
}
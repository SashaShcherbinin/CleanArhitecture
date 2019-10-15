package com.movies.popular.data.network.service

import com.movies.popular.data.network.model.DataDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("3/search/movie")
    fun getMoviesFound(@Query("query") query: String):
            Single<DataDto>
}
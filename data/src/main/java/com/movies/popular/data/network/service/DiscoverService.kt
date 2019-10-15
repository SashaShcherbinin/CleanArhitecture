package com.movies.popular.data.network.service

import com.movies.popular.data.network.model.DataDto
import com.movies.popular.data.network.model.MovieDetailDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscoverService {

    @GET("3/discover/movie")
    fun getMovies(@Query("sort_by") sortBy: String = "popularity.des",
                  @Query("page") page: Int,
                  @Query("with_genres") genres: String = "14")
            : Single<DataDto>


    @GET("3/movie/{movie_id}")
    fun getMovie(@Path("movie_id") id: Int)
            : Single<MovieDetailDto>


}
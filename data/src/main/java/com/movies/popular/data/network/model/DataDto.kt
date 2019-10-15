package com.movies.popular.data.network.model

import com.google.gson.annotations.SerializedName

data class DataDto(
        @SerializedName("page")
        val page: Int = 0,
        @SerializedName("total_pages")
        val totalPages: Int = 0,
        @SerializedName("results")
        val results: List<MovieDto>?,
        @SerializedName("total_results")
        val totalResults: Int = 0
)
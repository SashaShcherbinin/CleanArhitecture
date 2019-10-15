package com.movies.popular.domain.model

data class PageBundle<E>(val data: List<E>,
                         val hasNext: Boolean,
                         val maxCount: Int = 0,
                         val error: Throwable? = null)

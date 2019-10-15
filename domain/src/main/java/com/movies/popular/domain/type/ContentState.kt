package com.movies.popular.domain.type

enum class ContentState {

    CONTENT,
    LOADING,
    ERROR,
    EMPTY,
    FORBIDDEN;

    val isContent: Boolean get() = this == CONTENT

    val isLoading: Boolean get() = this == LOADING

    val isEmpty: Boolean get() = this == EMPTY

    val isError: Boolean get() = this == ERROR

    val isForbidden: Boolean get() = this == FORBIDDEN
}

package com.movies.popular.domain.exeption

open class APIException(val code: Int, message: String?) : Exception(message) {

    override fun toString(): String {
        return message ?: ""
    }
}

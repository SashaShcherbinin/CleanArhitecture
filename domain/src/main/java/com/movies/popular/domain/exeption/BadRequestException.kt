package com.movies.popular.domain.exeption

class BadRequestException(code: Int, message: String?) : APIException(code, message) {

    override fun toString(): String {
        return message ?: ""
    }
}

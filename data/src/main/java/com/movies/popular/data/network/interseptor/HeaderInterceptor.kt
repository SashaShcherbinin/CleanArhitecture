package com.movies.popular.data.network.interseptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor
    : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val newUrl = chain.request().url().newBuilder()
                .addQueryParameter("api_key",
                        "fbe4e6280f6a460beaad8ebe2bc130ac")
                .build()
        val newRequest = chain.request().newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }

}

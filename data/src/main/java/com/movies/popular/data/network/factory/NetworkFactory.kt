package com.movies.popular.data.network.factory

import com.google.gson.GsonBuilder
import com.movies.popular.data.BuildConfig
import com.movies.popular.data.network.RxErrorHandlingCallAdapterFactory
import com.movies.popular.data.network.interseptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkFactory {

    fun createGson() = GsonBuilder().create()!!

}

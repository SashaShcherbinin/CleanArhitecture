package com.movies.popular.data.di.module

import com.movies.popular.data.BuildConfig
import com.movies.popular.data.di.qualifier.QueryQualifier
import com.movies.popular.data.di.scope.DataScope
import com.movies.popular.data.network.RxErrorHandlingCallAdapterFactory
import com.movies.popular.data.network.factory.NetworkFactory
import com.movies.popular.data.network.interseptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @DataScope
    @Provides
    @QueryQualifier
    internal fun createRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.SCHEME + BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())

        builder.client(createOkHttpClient())

        return builder.build()
    }

    @Provides
    @QueryQualifier
    internal fun createGson() = NetworkFactory.createGson()

    @DataScope
    @Provides
    @QueryQualifier
    internal fun createOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(HeaderInterceptor())
        clientBuilder.connectTimeout(15, TimeUnit.SECONDS)
        clientBuilder.readTimeout(15, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(15, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
            clientBuilder.readTimeout(10, TimeUnit.SECONDS)
            clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(interceptor)
        }

        return clientBuilder.build()
    }

}


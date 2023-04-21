package com.example.weatherapppankaj.di

import com.example.weatherapppankaj.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun gson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    fun clientProvider(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .readTimeout(15,TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun retrofitProvider(
        gson: Gson,
        client: OkHttpClient,
        builder: OkHttpClient.Builder
    ):Retrofit{
        return  Retrofit.Builder()
            .baseUrl("")
            .client(builder.build())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun apiInterface(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


}
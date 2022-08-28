package com.fachmi.newsapiapp.data.source.remote.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private const val BASE_URL = "https://newsapi.org/v2/"
    private const val NETWORK_CALL_TIMEOUT = 120L

    private fun provideConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create().asLenient()
    }

    private fun provideHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            callTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    fun provideApiService(context: Context): ApiService {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
                .addConverterFactory(provideConverterFactory())
                .client(provideHttpClient(context))
        }.build().create(ApiService::class.java)
    }
}
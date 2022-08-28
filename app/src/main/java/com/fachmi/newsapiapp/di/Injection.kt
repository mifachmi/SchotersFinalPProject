package com.fachmi.newsapiapp.di

import android.content.Context
import com.fachmi.newsapiapp.data.repository.ArticleRepository
import com.fachmi.newsapiapp.data.source.remote.RemoteDataSource
import com.fachmi.newsapiapp.data.source.remote.network.ApiConfig

object Injection {

    fun provideArticleRepository(context: Context): ArticleRepository {

        val apiService = ApiConfig.provideApiService(context)
        val remoteDataSource = RemoteDataSource.getInstance(apiService)

        return ArticleRepository.getInstance(remoteDataSource)
    }
}
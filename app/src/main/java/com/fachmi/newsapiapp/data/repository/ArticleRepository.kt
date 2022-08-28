package com.fachmi.newsapiapp.data.repository

import androidx.lifecycle.LiveData
import com.fachmi.newsapiapp.data.source.remote.RemoteDataSource
import com.fachmi.newsapiapp.data.source.remote.response.ApiResponse
import com.fachmi.newsapiapp.data.source.remote.response.ArticleResponse

class ArticleRepository private constructor(
    private val remoteDataSource: RemoteDataSource
) {
    companion object {
        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
        ): ArticleRepository {
            return instance ?: synchronized(this) {
                instance ?: ArticleRepository(remoteDataSource)
            }
        }
    }

    fun getArticle(): LiveData<ApiResponse<ArticleResponse>> {
        return remoteDataSource.getNews()
    }

}
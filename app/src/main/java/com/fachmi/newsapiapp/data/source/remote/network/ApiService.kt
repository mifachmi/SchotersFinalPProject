package com.fachmi.newsapiapp.data.source.remote.network

import com.fachmi.newsapiapp.BuildConfig.NEWS_API_KEY
import com.fachmi.newsapiapp.data.source.remote.response.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("top-headlines?country=id&apiKey=${NEWS_API_KEY}")
    fun getArticle(): Call<ArticleResponse>

}
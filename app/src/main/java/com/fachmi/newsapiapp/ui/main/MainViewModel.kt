package com.fachmi.newsapiapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fachmi.newsapiapp.data.repository.ArticleRepository
import com.fachmi.newsapiapp.data.source.remote.response.ApiResponse
import com.fachmi.newsapiapp.data.source.remote.response.ArticleResponse

class MainViewModel(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    fun getArticle(): LiveData<ApiResponse<ArticleResponse>> {
        return articleRepository.getArticle()
    }
}

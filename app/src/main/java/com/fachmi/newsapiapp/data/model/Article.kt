package com.fachmi.newsapiapp.data.model

import com.fachmi.newsapiapp.data.source.remote.response.ArticleResponse

data class Article(
    val title: String,
    val author: String,
    val description: String,
    val urlArticle: String,
    val urlImage: String
)

fun ArticleResponse.toArticleList(): MutableList<Article> {
    return this.articles?.map { response ->
        Article(
            title = response?.title.orEmpty(),
            author = response?.author.orEmpty(),
            description = response?.description.orEmpty(),
            urlArticle = response?.url.orEmpty(),
            urlImage = response?.urlToImage.orEmpty()
        )
    }.orEmpty().toMutableList()
}

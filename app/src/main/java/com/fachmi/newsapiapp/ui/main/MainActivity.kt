package com.fachmi.newsapiapp.ui.main

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fachmi.newsapiapp.common.adapter.ArticleAdapter
import com.fachmi.newsapiapp.common.view.ViewModelFactory
import com.fachmi.newsapiapp.data.model.Article
import com.fachmi.newsapiapp.data.model.toArticleList
import com.fachmi.newsapiapp.data.source.remote.response.ApiResponse
import com.fachmi.newsapiapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val articleAdapter = ArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        configureRecycleView()
        bindingDataRecycleView()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun configureRecycleView() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
        }

        articleAdapter.setEventListener(object : ArticleAdapter.EventListener {
            override fun onItemClick(article: Article) {
                CustomTabsIntent.Builder().apply {
                    setShowTitle(true)
                    build().launchUrl(this@MainActivity, Uri.parse(article.urlArticle))
                }
            }
        })
    }

    private fun bindingDataRecycleView() {
        viewModel.getArticle().observe(this) { result ->
            when (result) {
                is ApiResponse.Success -> {
                    val articleList = result.data.toArticleList()
                    articleAdapter.submitList(articleList)
                }
                else -> {}
            }
        }
    }
}
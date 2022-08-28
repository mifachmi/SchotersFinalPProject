package com.fachmi.newsapiapp.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fachmi.newsapiapp.data.model.Article
import com.fachmi.newsapiapp.databinding.ItemArticleBinding

class ArticleAdapter :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var listener: EventListener? = null
    private var listArticle = mutableListOf<Article>()

    fun submitList(newList: MutableList<Article>) {
        listArticle.apply {
            notifyItemRangeRemoved(0, itemCount)
            clear()
            addAll(newList)
            notifyItemRangeChanged(0, itemCount)
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = listArticle[position]
        holder.bind(article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(article: Article) {
            binding.apply {
                initView(article)
                initClickListeners(article)
            }
        }

        private fun ItemArticleBinding.initView(article: Article) {
            Glide.with(context).load(article.urlImage).centerCrop().into(civImage)
            tvTitle.text = article.title
            tvAuthor.text = article.author
            tvDescription.text = article.description
        }

        private fun ItemArticleBinding.initClickListeners(article: Article) {
            root.setOnClickListener {
                listener?.onItemClick(article)
            }
        }
    }

    fun setEventListener(listener: EventListener?) {
        this.listener = listener
    }

    interface EventListener {

        fun onItemClick(article: Article)
    }

    override fun getItemCount(): Int {
        return listArticle.size
    }

}
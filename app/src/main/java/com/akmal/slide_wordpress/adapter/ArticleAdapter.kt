package com.akmal.slide_wordpress.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akmal.slide_wordpress.databinding.ItemArticleBinding
import com.akmal.slide_wordpress.model.Article
import com.bumptech.glide.Glide

class ArticleAdapter(
    private val articles: List<Article>,
    private val categoriesMap: Map<Int, String>,
    private val mediaMap: Map<Int, String>
): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    class ArticleViewHolder(val binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.binding.tvTitle.text = article.title.rendered
        holder.binding.tvCategory.text = article.categories.joinToString { categoriesMap[it] ?: "Unknown" }

        Glide.with(holder.itemView.context)
            .load(mediaMap[article.featured_media])
            .into(holder.binding.ivImage)
    }

}
package com.yondikavl.scancer.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.yondikavl.scancer.R
import com.yondikavl.scancer.models.Article

class NewsAdapter(private val context: Context, private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById(R.id.author)
        val title: TextView = view.findViewById(R.id.title)
        val publishedAt: TextView = view.findViewById(R.id.publishedAt)
        val cardView: CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.author.text = article.author
        holder.title.text = article.title
        holder.publishedAt.text = article.publishedAt
        holder.cardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(article.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = articles.size
}

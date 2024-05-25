package com.yondikavl.scancer.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yondikavl.scancer.api.RetrofitInstance
import com.yondikavl.scancer.models.Article
import com.yondikavl.scancer.models.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> get() = _articles

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchNews()
    }

    private fun fetchNews() {
        _loading.value = true
        val apiKey = "8616115510ab4b238fdc2f5016fe6baf"
        val call = RetrofitInstance.api.getTopHeadlines("id", "health", apiKey)

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _articles.value = response.body()?.articles
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _loading.value = false
                _error.value = t.message
            }
        })
    }
}

package com.yondikavl.scancer.api

import com.yondikavl.scancer.models.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}

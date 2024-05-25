package com.yondikavl.scancer.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yondikavl.scancer.adapter.NewsAdapter
import com.yondikavl.scancer.R

class NewsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        newsViewModel.articles.observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter = NewsAdapter(requireContext(), articles)
            recyclerView.adapter = newsAdapter
        })

//        newsViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
//            // Handle loading state, e.g., show/hide a progress bar
//        })
//
//        newsViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
//            // Handle error state, e.g., show a toast or a Snackbar
//        })

        return view
    }
}

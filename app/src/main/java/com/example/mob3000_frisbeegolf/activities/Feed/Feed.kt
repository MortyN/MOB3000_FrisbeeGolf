package com.example.mob3000_frisbeegolf.activities.Feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.activities.Feed.adapters.RecyclerViewAdapterFeed
import java.util.*
import kotlin.collections.List as List

class Feed : Fragment(){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    private fun initRecyclerView(view: View) {
        val itemList: MutableList<Post> = ArrayList()

        val post1 = Post("Post 1")
        val post2 = Post("Post 2")
        val post3 = Post("Post 3")
        val post4 = Post("Post 4")
        val post5 = Post("Post 5")

        itemList.add(post1)
        itemList.add(post2)
        itemList.add(post3)
        itemList.add(post4)
        itemList.add(post5)

        val adapter = RecyclerViewAdapterFeed(itemList)

        val recyclerView: RecyclerView = view.findViewById(R.id.feed_items)
        recyclerView.adapter = adapter
    }

    }

class Post(

    var message: String,

){

}
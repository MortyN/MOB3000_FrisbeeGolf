package com.example.mob3000_frisbeegolf.activities.MyProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.activities.MyProfile.adapters.RecyclerViewAdapterFeed

class MyProfile : Fragment() {

    lateinit var userPostField: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //userPostField = findViewById(R.id.userInputField)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initRecyclerView(view: View) {
        val itemList: MutableList<Post> = ArrayList()

        val post1 = Post("Hello 1")
        val post2 = Post("Hello 2")
        val post3 = Post("Hello 3")
        val post4 = Post("Hello 4")
        val post5 = Post("Hello 5")

        itemList.add(post1)
        itemList.add(post2)
        itemList.add(post3)
        itemList.add(post4)
        itemList.add(post5)

        /*val iteratePosts = (1..4).iterator();
        iteratePosts.forEach {
            val postCreated =  Post("Hello " + it)
            itemList.add(postCreated)
        }*/


        val  adapter = RecyclerViewAdapterFeed(itemList)

        val recyclerView: RecyclerView = view.findViewById(R.id.myProfile_items)
        recyclerView.adapter = adapter
    }

}

class Post(
    var message: String,
){

}
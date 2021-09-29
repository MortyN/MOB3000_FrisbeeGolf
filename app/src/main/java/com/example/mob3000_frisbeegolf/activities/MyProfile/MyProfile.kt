package com.example.mob3000_frisbeegolf.activities.MyProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.activities.MyProfile.adapters.RecyclerViewAdapterFeed

class MyProfile : Fragment() {

    lateinit var userPostField: EditText
    lateinit var postButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_myprofile, container, false)
    }

    private fun initRecyclerView(view: View) {

        userPostField = view.findViewById(R.id.myprofile_post_input_field)
        val itemList: MutableList<Post> = ArrayList()

        postButton = view.findViewById(R.id.myprofile_post_button)


        val post1 = Post("Nei vettuva, Vaer egt ikke såbra allikavel")
        val post2 = Post("Fantastisk vær på Vaer discogolfbane i dag, bygger hotell her neste uke!")
        val post3 = Post("Ræva bane!")
        val post4 = Post("Tilbake i norge. Blir gøy med litt frisbeegolf på vestlandet")
        val post5 = Post("Empire state building var spesielt morsomt!")

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
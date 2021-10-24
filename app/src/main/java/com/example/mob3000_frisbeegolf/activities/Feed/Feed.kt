package com.example.mob3000_frisbeegolf.activities.Feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.activities.Feed.adapters.FeedAdapter
import com.example.mob3000_frisbeegolf.api.constants.ApiConstants.Companion.APIHOST
import com.example.mob3000_frisbeegolf.api.constants.ApiConstants.Companion.APIPORT
import com.example.mob3000_frisbeegolf.api.constants.ApiConstants.Companion.APIPOSTPREFIX
import com.example.mob3000_frisbeegolf.api.endpoints.FeedClient
import com.example.mob3000_frisbeegolf.api.filter.PostFilter
import com.example.mob3000_frisbeegolf.api.model.Post
import com.example.mob3000_frisbeegolf.api.model.User
import com.example.mob3000_frisbeegolf.databinding.FragmentFeedBinding
import com.example.mob3000_frisbeegolf.model.FeedPost
import com.example.mob3000_frisbeegolf.model.Posttest
import com.example.mob3000_frisbeegolf.model.Usertest
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class Feed : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val user = PostFilter(User(110))

        val data = listOf(
            FeedPost("Post 1","user1","https://www.nbforum.com/wp-content/uploads/Petter-Stordalen.jpg"),
            FeedPost("Post 1","user1","https://g.acdn.no/obscura/API/dynamic/r1/nadp/tr_2000_2000_s_f/0000/2017/07/06/3423355117/1/original/3424477.jpg?chk=7C360A")
        )


        binding = FragmentFeedBinding.inflate(layoutInflater)

        manager = LinearLayoutManager(context)

        binding.recyclerView.apply {
            adapter = FeedAdapter(data as MutableList<FeedPost>)
            layoutManager = manager

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = PostFilter(User(110))
        sendRequest(user)
        super.onViewCreated(view, savedInstanceState)
    }

    fun sendRequest(user: PostFilter){
        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl(APIHOST+ APIPORT+ APIPOSTPREFIX)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit: Retrofit = builder.build()
        val feed: FeedClient = retrofit.create(FeedClient::class.java)
        val call: Call<List<Posttest>> = feed.user(user)

        call.enqueue(object : Callback<List<Posttest>>{
            override fun onResponse(call: Call<List<Posttest>>, response: Response<List<Posttest>>) {
               val res = response.body()
                print(res)
            }

            override fun onFailure(call: Call<List<Posttest>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@Feed.context, "Failed", Toast.LENGTH_LONG).show()
            }


        })
    }

//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initRecyclerView(view)
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_feed, container, false)
//    }
//
//    private fun initRecyclerView(view: View) {
//        val itemList: MutableList<Post> = ArrayList()
//
//        val post1 = Post("Post 1")
//        val post2 = Post("Post 2")
//        val post3 = Post("Post 3")
//        val post4 = Post("Post 4")
//        val post5 = Post("Post 5")
//
//        itemList.add(post1)
//        itemList.add(post2)
//        itemList.add(post3)
//        itemList.add(post4)
//        itemList.add(post5)
//
//
//        val adapter = FeedAdapter(itemList)
//
//        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.adapter = adapter
//    }

}


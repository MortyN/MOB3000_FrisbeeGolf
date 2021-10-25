package com.example.mob3000_frisbeegolf.activities.feed

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mob3000_frisbeegolf.activities.feed.adapters.FeedAdapter
import com.example.mob3000_frisbeegolf.api.constants.ApiConstants
import com.example.mob3000_frisbeegolf.api.endpoints.APIFeedInterface
import com.example.mob3000_frisbeegolf.api.filter.PostFilterByUser
import com.example.mob3000_frisbeegolf.api.model.PostResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FeedViewModel: ViewModel() {
    var emptyList: List<PostResponse> = emptyList()
    lateinit var recyclerListData: MutableLiveData<List<PostResponse>>
    lateinit var recyclerViewAdapter: FeedAdapter

    init {
        recyclerListData = MutableLiveData()
        recyclerViewAdapter = FeedAdapter()
    }

    fun getAdapter(): FeedAdapter{
        return recyclerViewAdapter
    }

    fun setAdapterData(data: List<PostResponse>){
        recyclerViewAdapter.setItems(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<List<PostResponse>>{
        return recyclerListData
    }

    fun sendRequest(user: PostFilterByUser, context: Context?): List<PostResponse>{
        val builder: Retrofit.Builder = Retrofit.Builder()
        builder.baseUrl(ApiConstants.APIHOST + ApiConstants.APIPORT + ApiConstants.APIPOSTPREFIX)
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit: Retrofit = builder.build()
        val feed: APIFeedInterface = retrofit.create(APIFeedInterface::class.java)

        val json: String = Gson().toJson(user)
        val call: Call<List<PostResponse>> = feed.getFeed(user)

        call.enqueue(object : Callback<List<PostResponse>> {
            override fun onResponse(call: Call<List<PostResponse>>, response: Response<List<PostResponse>>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                }else{
                    recyclerListData.postValue(null)
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(context, "Welcome", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<List<PostResponse>>, t: Throwable) {
                t.printStackTrace()
                recyclerListData.postValue(null)
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            }


        })
        return emptyList
    }
}
package com.example.mob3000_frisbeegolf.activities.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.BR
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.api.filter.PostFilterByUser
import com.example.mob3000_frisbeegolf.api.model.PostResponse
import com.example.mob3000_frisbeegolf.api.model.User
import com.example.mob3000_frisbeegolf.databinding.FragmentFeedBinding
import java.util.*

class Feed : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var feedViewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        binding = FragmentFeedBinding.inflate(layoutInflater)
//        manager = LinearLayoutManager(context)
        val viewModel = getFeedItems()



//        return binding.root
        return setupBinding(viewModel)
    }

    fun setupBinding(viewModel: FeedViewModel): View{
        binding = FragmentFeedBinding.inflate(layoutInflater)
//        val activityFeedBiding: FragmentFeedBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_feed)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@Feed.context)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val user = PostFilterByUser(User(110))
//        val data: List<PostResponse> = sendRequest(user, this@Feed.context)
//        binding.recyclerView.apply {
//            adapter = FeedAdapter()
//            layoutManager = manager
//
//        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun getFeedItems(): FeedViewModel{
        val user = PostFilterByUser(User(110), null, null, true)

        val viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.getRecyclerListDataObserver().observe(viewLifecycleOwner, Observer<List<PostResponse>>{
            if (it != null){
                binding.progressbar.visibility = GONE
                viewModel.setAdapterData(it)
            }else{
                Toast.makeText(this@Feed.context, "Error fetching data", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.sendRequest(user, this@Feed.context)
        return viewModel
    }


}


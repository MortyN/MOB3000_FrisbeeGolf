package com.example.mob3000_frisbeegolf.activities.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mob3000_frisbeegolf.BR
import com.example.mob3000_frisbeegolf.api.filter.PostFilterByUser
import com.example.mob3000_frisbeegolf.api.model.User
import com.example.mob3000_frisbeegolf.databinding.FragmentFeedBinding
import java.util.*

open class FadeInLinearLayoutManager(context: Context?) : LinearLayoutManager(context) {
    private val enterInterpolator = AnticipateOvershootInterpolator(2f)

    override fun addView(child: View, index: Int) {
        super.addView(child, index)
        val h = 400f
        // if index == 0 item is added on top if -1 it's on the bottom
        child.translationY = if(index == 0) 0f else h
        // begin animation when view is laid out
        if(index == 0) return
        child.alpha = 0.3f
        child.animate().translationY(0f).alpha(3f)
            .setInterpolator(enterInterpolator).duration = 1000L
    }
}

class Feed : Fragment() {

    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = getFeedItems()
        return setupBinding(viewModel)
    }

    fun setupBinding(viewModel: FeedViewModel): View{
        binding = FragmentFeedBinding.inflate(layoutInflater)

        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        binding.recyclerView.apply {
              layoutManager = FadeInLinearLayoutManager(this@Feed.context)
        }

        return binding.root
    }

    fun getFeedItems(): FeedViewModel{
        val user = PostFilterByUser(User(110), null, null, true)

        val viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.getRecyclerListDataObserver().observe(viewLifecycleOwner, {
            if (it != null){
                binding.progressbar.visibility = GONE
                binding.loadinglogo.visibility = GONE
                viewModel.setAdapterData(it)
            }else{
                Toast.makeText(this@Feed.context, "Error fetching data", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.sendRequest(user, this@Feed.context)
        return viewModel
    }

}


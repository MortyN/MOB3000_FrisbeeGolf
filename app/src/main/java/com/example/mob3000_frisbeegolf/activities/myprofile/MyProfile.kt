package com.example.mob3000_frisbeegolf.activities.myprofile

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
import com.example.mob3000_frisbeegolf.databinding.FragmentMyprofileBinding
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

class MyProfile : Fragment() {

    private lateinit var binding: FragmentMyprofileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = getFeedItems()
        return setupBinding(viewModel)
    }

    fun setupBinding(viewModel: MyProfileViewModel): View{
        binding = FragmentMyprofileBinding.inflate(layoutInflater)

        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
        binding.myProfileRecyclerview.apply {
              layoutManager = FadeInLinearLayoutManager(this@MyProfile.context)
        }

        return binding.root
    }

    fun getFeedItems(): MyProfileViewModel{
        val user = PostFilterByUser(User(110), null, null, false)

        val viewModel = ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
        viewModel.getRecyclerListDataObserver().observe(viewLifecycleOwner, {
            if (it != null){
                binding.myprofileProgressbar.visibility = GONE
                binding.myprofileLoadinglogo.visibility = GONE
                viewModel.setAdapterData(it)
            }else{
                Toast.makeText(this@MyProfile.context, "Error fetching data", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.sendRequest(user, this@MyProfile.context)
        return viewModel
    }

}


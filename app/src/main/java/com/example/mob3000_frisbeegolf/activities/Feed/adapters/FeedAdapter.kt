package com.example.mob3000_frisbeegolf.activities.Feed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.databinding.ListitemRowContainerBinding
import com.example.mob3000_frisbeegolf.model.FeedPost

class FeedAdapter(private val items: List<FeedPost>) : RecyclerView.Adapter<FeedAdapter.MyViewHolder>() {

    inner class MyViewHolder(private var binding: ListitemRowContainerBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(p: FeedPost) {
            binding.post = p
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ListitemRowContainerBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
       return items.size
    }


}
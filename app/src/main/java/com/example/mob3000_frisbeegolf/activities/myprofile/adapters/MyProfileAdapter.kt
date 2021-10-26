package com.example.mob3000_frisbeegolf.activities.myprofile.adapters

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.api.model.PostResponse
import com.example.mob3000_frisbeegolf.databinding.ListitemRowContainerBinding
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


class MyProfileAdapter : RecyclerView.Adapter<MyProfileAdapter.MyViewHolder>() {

    private var items = emptyList<PostResponse>()

    fun setItems(data: List<PostResponse>) {
        this.items = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ListitemRowContainerBinding.inflate(inflater, parent, false)
        return MyViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MyViewHolder(private var binding: ListitemRowContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: PostResponse) {
            binding.post = p
            binding.executePendingBindings()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    companion object {
        @JvmStatic
        @BindingAdapter("loadMyProfileImage")
        fun loadMyProfileImage(fragment_feed_profileImg: ImageView, imgKey: String) {
            Glide.with(fragment_feed_profileImg)
                .load("https://prod-disky-images.s3.eu-north-1.amazonaws.com/$imgKey.jpeg")
                .circleCrop().placeholder(R.mipmap.sym_def_app_icon)
                .error(R.mipmap.sym_def_app_icon).fallback(R.drawable.sym_def_app_icon)
                .into(fragment_feed_profileImg)
        }
    }

}
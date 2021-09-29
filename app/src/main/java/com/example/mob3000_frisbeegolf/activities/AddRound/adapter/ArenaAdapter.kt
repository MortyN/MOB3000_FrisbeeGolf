package com.example.mob3000_frisbeegolf.activities.AddRound.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.databinding.RowItemBinding
import com.example.mob3000_frisbeegolf.model.Arena

class ArenaAdapter(
    var arenaList: List<Arena>
    ) : RecyclerView.Adapter<ArenaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arenaList[position])
    }

    override fun getItemCount(): Int = arenaList.size

    inner class ViewHolder(private var arenaItem: RowItemBinding) : RecyclerView.ViewHolder(arenaItem.root) {
        fun bind(arena: Arena) {
            arenaItem.arenaName.text = arena.name;
        }
    }

}
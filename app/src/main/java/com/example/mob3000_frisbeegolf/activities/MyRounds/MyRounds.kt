package com.example.mob3000_frisbeegolf.activities.MyRounds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.activities.Feed.adapters.RecyclerViewAdapter

class MyRounds : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_myrounds, container, false)
    }

    private fun initRecyclerView(view: View) {
        val itemList = arrayListOf<String>("first", "sec", "third", "first", "sec", "third")
        val adapter = RecyclerViewAdapter(itemList)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
    }
}
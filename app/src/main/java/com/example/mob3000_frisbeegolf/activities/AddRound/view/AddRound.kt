package com.example.mob3000_frisbeegolf.activities.AddRound.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.activities.AddRound.adapter.ArenaAdapter
import com.example.mob3000_frisbeegolf.model.Arena
import java.time.Instant
import java.util.*

class AddRound : Fragment(){

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ArenaAdapter.ViewHolder>? = null
    private var recyclerView: RecyclerView? = null

    var arenaList: List<Arena> = Arrays.asList(
        Arena(1L, "Vear bane", "Dette er en bane", Date.from(Instant.now()), 1L, Date.from(Instant.now()), Date.from(Instant.now())),
        Arena(1L, "TBG bane", "Dette er en bane", Date.from(Instant.now()), 1L, Date.from(Instant.now()), Date.from(Instant.now())),
        Arena(1L, "Larvik bane", "Dette er en bane", Date.from(Instant.now()), 1L, Date.from(Instant.now()), Date.from(Instant.now())),
        Arena(1L, "Oslo bane", "Dette er en bane", Date.from(Instant.now()), 1L, Date.from(Instant.now()), Date.from(Instant.now())),
        Arena(1L, "Bergen bane", "Dette er en bane", Date.from(Instant.now()), 1L, Date.from(Instant.now()), Date.from(Instant.now())),
        Arena(1L, "Grimstad bane", "Dette er en bane", Date.from(Instant.now()), 1L, Date.from(Instant.now()), Date.from(Instant.now()))
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_addround, container, false)
        recyclerView = view.findViewById(R.id.arenaRecycler)
        recyclerView?.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(activity)
            adapter = ArenaAdapter(arenaList)
        }
        return view
    }
}
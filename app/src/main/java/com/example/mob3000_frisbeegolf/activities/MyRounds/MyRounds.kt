package com.example.mob3000_frisbeegolf.activities.MyRounds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R

import kotlin.random.Random

class MyRounds : Fragment() {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        initRecyclerView(view)
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        return inflater.inflate(R.layout.fragment_myrounds, container, false)
//    }
//
//    private fun initRecyclerView(view: View) {
//        var holeList : MutableList<HoleList> = mutableListOf()
//
//        for (i in 1..20) {
//            holeList.add(HoleList(i, Random.nextInt(2, 10)))
//        }
//
//        val itemList = arrayListOf<Round>(Round("Vear Disc Golf", holeList as ArrayList<HoleList>, "Gamerboi69"), Round("Tønsberg Disc Golf", holeList as ArrayList<HoleList>, "EpiskgaimrxX"))
//        val adapter =  MyRoundsAdapter(itemList)
//
//        val recyclerView: RecyclerView = view.findViewById(R.id.myrounds_recycler_view)
//        recyclerView.adapter = adapter
//    }
}
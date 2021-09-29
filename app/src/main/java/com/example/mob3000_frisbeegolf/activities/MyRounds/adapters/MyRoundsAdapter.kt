package com.example.mob3000_frisbeegolf.activities.MyRounds.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.models.HoleList
import com.example.mob3000_frisbeegolf.models.Round


class MyRoundsAdapter(private val dataSet: ArrayList<Round>) :
    RecyclerView.Adapter<MyRoundsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val arenaNameTextView: TextView = view.findViewById(R.id.myrounds_textview_arenaname)
        val tableLayout: TableLayout = view.findViewById(R.id.myrounds_tablelayout)

        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.myrounds_tablelayout_container, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val inflater: LayoutInflater = LayoutInflater.from(viewHolder.view.context)
        val holeList: ArrayList<HoleList> = dataSet[position].HoleList

        holeList.forEach { e ->
            run {
                val tableRow = inflater.inflate(
                    R.layout.myrounds_table_row,
                    viewHolder.tableLayout,
                    false
                ) as TableRow
                tableRow.findViewById<TextView>(R.id.myrounds_table_col1).text = e.HoleNr.toString()
                tableRow.findViewById<TextView>(R.id.myrounds_table_col2).text = e.Throws.toString()
                viewHolder.tableLayout.addView(tableRow)
            }
        }

        // Get element from your dataset at this position
        viewHolder.arenaNameTextView.text = dataSet[position].ArenaName

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
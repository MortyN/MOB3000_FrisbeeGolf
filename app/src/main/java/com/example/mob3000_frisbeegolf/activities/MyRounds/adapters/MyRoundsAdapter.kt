package com.example.mob3000_frisbeegolf.activities.MyRounds.adapters

import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mob3000_frisbeegolf.R
import com.example.mob3000_frisbeegolf.models.Round
import org.w3c.dom.Text

class MyRoundsAdapter(private val dataSet: ArrayList<Round>) :
    RecyclerView.Adapter<MyRoundsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        val arenaNameTextView: TextView = view.findViewById(R.id.myrounds_textview_arenaname)
        val tableLayout: TableLayout = view.findViewById(R.id.myrounds_tablelayout)

        val tableRow: TableRow = TableRow(tableLayout.context)
        val textView: TextView = TextView(view.context)



        init {
            // Define click listener for the ViewHolder's View.
        }
    }

    fun populateTable(view:View, tableLayout: TableLayout){

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.listitem_table_row_container, viewGroup, false)


        populateTable(viewGroup.rootView)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.arenaNameTextView.text = dataSet[position].ArenaName

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
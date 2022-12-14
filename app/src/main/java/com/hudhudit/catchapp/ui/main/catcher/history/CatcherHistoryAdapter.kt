package com.hudhudit.catchapp.ui.main.catcher.history

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.catchee.history.CatcheeOrderHistory
import com.hudhudit.catchapp.apputils.modules.catchee.notifications.CatcheeNotification
import com.hudhudit.catchapp.apputils.modules.catcher.history.CatcherOrderHistory

class CatcherHistoryAdapter(
    private var catcherHistoryFragment: CatcherHistoryFragment,
    private var orders: MutableList<CatcherOrderHistory>
) :
    RecyclerView.Adapter<CatcherHistoryAdapter.ViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.history_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val order = orders[position]

        holder.catcherName.text = order.client_name
        holder.nameTitle.text = context!!.resources.getString(R.string.catchee_name)
        holder.catchDateTime.text = order.date+", "+order.time
        if (order.total_price.isEmpty()){
            holder.catchFeesLayout.visibility = View.GONE
        }else{
            holder.catchFeesLayout.visibility = View.VISIBLE
            holder.catchFees.text = order.total_price
        }

    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var catcherName: TextView = itemView.findViewById(R.id.catcher_name)
        var nameTitle: TextView = itemView.findViewById(R.id.name_title)
        var catchFees: TextView = itemView.findViewById(R.id.catch_fees)
        var catchDateTime: TextView = itemView.findViewById(R.id.catch_time_date)
        var catchFeesLayout: LinearLayoutCompat = itemView.findViewById(R.id.catch_fees_layout)
    }

}
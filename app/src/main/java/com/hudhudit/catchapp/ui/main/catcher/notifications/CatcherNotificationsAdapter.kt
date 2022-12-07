package com.hudhudit.catchapp.ui.main.catcher.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.hudhudit.catchapp.R
import com.hudhudit.catchapp.apputils.modules.catcher.notifications.CatcherNotification

class CatcherNotificationsAdapter(
    private var notificationsFragment: CatcherNotificationsFragment,
    private var notifications: MutableList<CatcherNotification>
) :
    RecyclerView.Adapter<CatcherNotificationsAdapter.ViewHolder>() {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.notification_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val notification = notifications[position]

        holder.title.text = notification.title
        holder.description.text = notification.description
        holder.notificationDate.text = notification.date+", "+notification.times

//        if (notification.driver_info != null){
//            holder.goToPayment.visibility = View.VISIBLE
//        }else{
//            holder.goToPayment.visibility = View.GONE
//        }

    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.notification_title)
        var notificationDate: TextView = itemView.findViewById(R.id.notification_date_time)
        var description: TextView = itemView.findViewById(R.id.notification_description)
        var goToPayment: MaterialButton = itemView.findViewById(R.id.go_payment)
    }

}
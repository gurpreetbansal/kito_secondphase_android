package com.diggytech.kinoplasticpremios.Notification

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.notification_item.view.*


class NotificationAdapter(val context: Activity, val list: MutableList<ModelNotification>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[p1]
        holder.title.text = Html.fromHtml(model.title)
        holder.tvTime.text = model.time
        holder.ivLogo.setActualImageResource(model.img)

    }


    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val title = view.tvTitle as TextView
        val ivLogo = view.ivLogo as SimpleDraweeView
        val tvTime = view.tvTime as TextView

    }
}

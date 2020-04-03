package com.diggytech.kinoplasticpremios.MySpace

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.points_earnt_item.view.*


class PointsEarntAdapter(val context: Activity, val list: List<ModelPointsEarnt>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PointsEarntAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.points_earnt_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[p1]
        holder.tvPoints.text = model.points
        holder.tvClaimedOn.text = model.claimed_on
        holder.tvExpiresOn.text = model.expires_on
        holder.tvStatus.text = model.status

        when (model.status.toUpperCase()) {
            "APROVADO"-> {
                holder.tvStatus.setTextColor(context.resources.getColor(R.color.colorGreen))
            }
            "PENDENTE" -> {
                holder.tvStatus.setTextColor(context.resources.getColor(R.color.colorGrey))
            }
            "DECLINADO" -> {
                holder.tvStatus.setTextColor(context.resources.getColor(R.color.colorRed))
            }
        }

        if (p1 % 2 == 0) {
            holder.mainLayout.setBackgroundResource(R.color.colorWhite)
        } else {
            holder.mainLayout.setBackgroundResource(R.color.light_bg_grey)
        }
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val tvPoints = view.tvPoints as TextView
        val tvClaimedOn = view.tvClaimedOn as TextView
        val tvExpiresOn = view.tvExpiresOn as TextView
        val tvStatus = view.tvStatus as TextView
        val mainLayout = view.mainLayout as LinearLayout

    }
}

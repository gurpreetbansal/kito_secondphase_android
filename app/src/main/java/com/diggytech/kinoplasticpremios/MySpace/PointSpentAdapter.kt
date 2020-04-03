package com.diggytech.kinoplasticpremios.MySpace

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.points_spent_item.view.*


class PointSpentAdapter(val context: Activity, val list: List<ModelPointsSpent>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<PointSpentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.points_spent_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[p1]
        holder.tvPoints.text = model.points
        holder.tvCost.text = model.cost
        holder.tvGiftcard.text = model.gift_card
        holder.tvRedemtion.text = model.redemption_date

        if (p1 % 2 == 0) {
            holder.mainLayout.setBackgroundResource(R.color.colorWhite)
        } else {
            holder.mainLayout.setBackgroundResource(R.color.light_bg_grey)
        }
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val tvPoints = view.tvPoints as TextView
        val tvCost = view.tvCost as TextView
        val tvGiftcard = view.tvGiftcard as TextView
        val tvRedemtion = view.tvRedemtion as TextView
        val mainLayout = view.mainLayout as LinearLayout
    }
}

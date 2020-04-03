package com.diggytech.kinoplasticpremios.RedeemPrizes

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.RedeemPrizes.GiftCards.GiftCardsActivity
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.campaign_item.view.ivImage
import kotlinx.android.synthetic.main.campaign_item.view.name
import kotlinx.android.synthetic.main.redeem_prize_item.view.*


class RedeemPrizesAdapter(val context: Activity, val list: List<ModelRedeemPrize>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<RedeemPrizesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.redeem_prize_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[p1]
        holder.name.text = model.title
        holder.tvPrice.text = model.price_range
        holder.tvTime.text = model.validity_period
        holder.ivImage.setImageURI(model.image)

        holder.btnRedeem.setOnClickListener {
            val intent = Intent(context, GiftCardsActivity::class.java)
            intent.putExtra("model", model as Parcelable)
            context.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val name = view.name as TextView
        val tvPrice = view.tvPrice as TextView
        val tvTime = view.tvTime as TextView
        val ivImage = view.ivImage as SimpleDraweeView
        val btnRedeem = view.btnRedeem as TextView

    }
}

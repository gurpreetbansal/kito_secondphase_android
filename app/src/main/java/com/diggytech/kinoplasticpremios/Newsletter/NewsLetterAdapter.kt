package com.diggytech.kinoplasticpremios.Newsletter

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.text.Html
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.diggytech.kinoplasticpremios.R

import com.diggytech.kinoplasticpremios.RedeemPrizes.GiftCards.GiftCardsActivity
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.campaign_item.view.ivImage
import kotlinx.android.synthetic.main.campaign_item.view.name
import kotlinx.android.synthetic.main.fragment_my_space.view.*
import kotlinx.android.synthetic.main.notification_item_two.view.*
import kotlinx.android.synthetic.main.redeem_prize_item.view.*
import java.text.SimpleDateFormat


class NewsLetterAdapter(val context: Activity, val list: List<ModelNewsLetter>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<NewsLetterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item_two, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[p1]
        holder.name.text = model.title

        var des=model.message
        val description = Html.fromHtml(des).toString()
        holder.tvPrice.setText(description)
        val string: String =  model.created_at
        val yourArray: List<String> = string.split(" ")
       // holder.tvPrice.text = model.message
        holder.tvTime.text = yourArray[0]


//        val gifUrl = "http://part/to/your/gifFile.gif"
        Glide.with(context!!)
            .load(model.cover_pic)
            .into(holder.ivImage);

    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val name = view.name_notification as TextView
        val tvPrice = view.tvPrice_notification as TextView
        val tvTime = view.tvTime_notification as TextView
        val ivImage = view.ivImage_notification as ImageView


    }
}

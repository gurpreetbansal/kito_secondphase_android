package com.diggytech.kinoplasticpremios.Profile.EditProfile

import com.diggytech.kinoplasticpremios.R


import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diggytech.kinoplasticpremios.model.LocationArryForUserTwo_Three
import kotlinx.android.synthetic.main.custom_view_location.view.*


class Location_Adapter_Profile(val items : ArrayList<LocationArrayForUserTwo_Three_Profile>, val context: Context) : RecyclerView.Adapter<ViewHolderMyjobsProfile>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderMyjobsProfile
    {

        return ViewHolderMyjobsProfile(LayoutInflater.from(context).inflate(R.layout.custom_view_location, p0, false))
    }

    override fun onBindViewHolder(p0: ViewHolderMyjobsProfile, position: Int)
    {



        p0?.tvaddress?.text = items[position].getNames()

        var brand_names_profile= items[position].getNames()











        p0.img_cross.setOnClickListener {
            items.removeAt(p0.getAdapterPosition());
            notifyDataSetChanged();
        }

    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolderMyjobsProfile (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvaddress = view.txt_location_name
    val img_cross = view.img_cross

}
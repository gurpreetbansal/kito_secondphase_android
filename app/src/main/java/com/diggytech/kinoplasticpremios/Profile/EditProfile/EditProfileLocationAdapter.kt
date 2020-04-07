package com.diggytech.kinoplasticpremios.Profile.EditProfile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponseUserLocation
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.model.LocationArryForUserTwo_Three
import kotlinx.android.synthetic.main.custom_view_location.view.*

class EditProfileLocationAdapter(val items : ArrayList<ProfileResponseUserLocation>, val context: Context) : RecyclerView.Adapter<ViewHolderMyjobs>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderMyjobs
    {

        return ViewHolderMyjobs(LayoutInflater.from(context).inflate(R.layout.custom_view_location, p0, false))
    }

    override fun onBindViewHolder(p0: ViewHolderMyjobs, position: Int)
    {



        p0?.tvaddress?.text = items[position].Brand
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

class ViewHolderMyjobs (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvaddress = view.txt_location_name
    val img_cross = view.img_cross

}
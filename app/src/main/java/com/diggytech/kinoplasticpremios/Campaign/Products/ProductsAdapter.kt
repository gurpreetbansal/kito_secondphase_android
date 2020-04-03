package com.diggytech.kinoplasticpremios.Campaign.Products


import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.products_row_list.view.*

class ProductsAdapter(val context: Activity, val type: String, val list: List<ModelProducts>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        if (type == "1") {
            view = LayoutInflater.from(context).inflate(R.layout.products_row_list, parent, false)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.products_row_grid, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[holder.adapterPosition]
        holder.name.text = model.product_name
        holder.points.text = model.points_per_sale
        holder.image.setImageURI(model.product_image)
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val name = view.name as TextView
        val points = view.points as TextView
        val image = view.image as SimpleDraweeView
    }


}
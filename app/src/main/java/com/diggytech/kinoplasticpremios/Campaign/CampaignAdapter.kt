package com.diggytech.kinoplasticpremios.Campaign

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Campaign.Products.ProductsActivity
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.RegisterSale.RegisterSaleActivity
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.campaign_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CampaignAdapter(val context: Activity, val list: List<ModelCampaign>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.campaign_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val model = list[p1]
        holder.name.text = model.name
        holder.startOn.text = model.start_date
        holder.endOn.text = model.end_date
        holder.ivImage.setImageURI(model.image)
        checkDate(model.start_date, holder)

        holder.ivImage.setOnClickListener {
            val intent = Intent(context, ProductsActivity::class.java)
            intent.putExtra("id", model.id)
            context.startActivity(intent)
        }

        var  user_type = Constants.getPrefs(context)!!.getString(Constants.USER_TYPE, "")
        // var from_where_come = PreferenceManager.getDefaultSharedPreferences(this).getString("USER_TYPESS", "")
        Log.e("USER_TYPE_IS",user_type)

        if(user_type.equals("1"))
        {
            holder.btnRegisterSale.visibility=View.VISIBLE
            holder.btnRegisterSale.setOnClickListener {
                val intent = Intent(context, RegisterSaleActivity::class.java)
                intent.putExtra("id", model.id)
                context.startActivity(intent)
            }
        }
        else {
            holder.btnRegisterSale.visibility=View.GONE
        }







    }

    private fun checkDate(
        startDate: String,
        holder: ViewHolder
    ) {
        val c = Calendar.getInstance()

        // set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        // and get that as a Date
        val today = c.time  // current date

       // start date
        try {
            val curFormater = SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH)
            val date = curFormater.parse(startDate)

            val dayOfMonth = DateFormat.format("dd", date) as String // 20
            val month = DateFormat.format("MM", date) as String // 06
            val year = DateFormat.format("yyyy", date) as String // 2013

           // use the calendar to set start date
            val c1 = Calendar.getInstance()
            c1.set(Calendar.YEAR, year.toInt())
            c1.set(Calendar.MONTH, month.toInt() - 1)
            c1.set(Calendar.DAY_OF_MONTH, dayOfMonth.toInt())

          // and get that as a Date
            val dateSpecified = c1.time  // start date

           // test  condition
            if (dateSpecified.after(today)) {
                holder.btnRegisterSale.visibility = View.GONE
            } else {
                holder.btnRegisterSale.visibility = View.VISIBLE
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val name = view.name as TextView
        val startOn = view.startOn as TextView
        val endOn = view.endOn as TextView
        val ivImage = view.ivImage as SimpleDraweeView
        val btnRegisterSale = view.btnRegisterSale as TextView

    }
}

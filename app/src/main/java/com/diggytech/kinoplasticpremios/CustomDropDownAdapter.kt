package com.diggytech.kinoplasticpremios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.ImageView


class CustomDropDownAdapter(internal var context: Context, internal var images: IntArray, internal var fruit: IntArray) :
    BaseAdapter() {
    internal var inflter: LayoutInflater

    init {
        inflter = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        var view = view
        view = inflter.inflate(R.layout.view_drop_down_menu, null)
        val icon = view.findViewById(R.id.imageView) as ImageView
       // val names = view.findViewById(R.id.textView) as TextView
        icon.setImageResource(images[i])
       // names.text = fruit[i].toString()
        return view
    }
}



//class CustomDropDownAdapter(
//    val context: Context,
//    var listItemsTxt: Array<String>,
//    images: IntArray
//) : BaseAdapter() {
//
//
//    val mInflater: LayoutInflater = LayoutInflater.from(context)
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view: View
//        val vh: ItemRowHolder
//        if (convertView == null) {
//            view = mInflater.inflate(R.layout.view_drop_down_menu, parent, false)
//            vh = ItemRowHolder(view)
//            view?.tag = vh
//        } else {
//            view = convertView
//            vh = view.tag as ItemRowHolder
//        }
//
//        // setting adapter item height programatically.
//
//        val params = view.layoutParams
//        params.height = 60
//        view.layoutParams = params
//
//        vh.label.text = listItemsTxt.get(position)
//        return view
//    }
//
//    override fun getItem(position: Int): Any? {
//
//        return null
//
//    }
//
//    override fun getItemId(position: Int): Long {
//
//        return 0
//
//    }
//
//    override fun getCount(): Int {
//        return listItemsTxt.size
//    }
//
//    private class ItemRowHolder(row: View?) {
//
//        val label: TextView
//
//        init {
//            this.label = row?.findViewById(R.id.txtDropDownLabel) as TextView
//        }
//    }
//}
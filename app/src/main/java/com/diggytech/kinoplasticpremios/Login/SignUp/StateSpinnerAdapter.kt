package com.diggytech.kinoplasticpremios.Login.SignUp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R

//import com.imarkinfotech.stronghold.R
//import com.imarkinfotech.stronghold.model.ClientListData
//import com.imarkinfotech.stronghold.model.TaskListData

class StateSpinnerAdapter(val context: Context, var arrayList: List<StateListData>) : BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.custom_view_add_task, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        }
        else
        {
            view = convertView
            vh = view.tag as ItemRowHolder
        }

        val params = view.layoutParams
        params.height = 60
        view.layoutParams = params

        vh.label.text = arrayList.get(position).state

        return view
    }

    override fun getItem(position: Int): Any? {
        return null

    }

    override fun getItemId(position: Int): Long {
        return 0

    }

    override fun getCount(): Int {
        return arrayList.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView

        init
        {
            this.label = row?.findViewById(R.id.txt_task_list) as TextView
        }
    }
}
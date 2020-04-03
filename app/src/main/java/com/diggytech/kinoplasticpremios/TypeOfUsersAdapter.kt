package com.diggytech.kinoplasticpremios

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.demo_spinner.view.*

class TypeOfUsersAdapter(ctx: Context,
                       moods: List<TypeOfUsers>) :
    ArrayAdapter<TypeOfUsers>(ctx, 0, moods) {

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {

        val mood = getItem(position)

        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.type_of_user,
            parent,
            false
        )

      //  view.moodImage.setImageResource(mood.image)
        view.moodText.text = mood.description

        return view
    }
}
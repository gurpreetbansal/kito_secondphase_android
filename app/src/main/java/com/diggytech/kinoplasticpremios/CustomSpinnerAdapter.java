package com.diggytech.kinoplasticpremios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<LocationsModel>  {
    Context context;
    private List<LocationsModel> objects = new ArrayList<>();

    private final Spinner spinner;


    public CustomSpinnerAdapter(Context context, int textViewResourceId, List<LocationsModel> objects,
                                Spinner spinner) {
        super(context, 0, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
        this.spinner = spinner;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(spinner.getSelectedItemPosition(), convertView, parent);
    }


    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        final LocationsModel LocationsModel = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dropdown_recycler_item, parent, false);
        TextView label = row.findViewById(R.id.title);
        label.setText(LocationsModel.getName());
//label.setOnClickListener(this);

//label.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        //Toast.makeText(context, "CLICKED", Toast.LENGTH_SHORT).show();
//        //ButtonCallBack.getInstance(context).onButtonListener();
//    }
//});
        return row;
    }

//   @Override
//    public void onClick(View view) {
//
//    }
}
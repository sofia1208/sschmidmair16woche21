package com.example.sschmidmair16woche21;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Rechnung_Adapter extends ArrayAdapter<Rechnung> {
private static final String Tag = "Rechnung_Adapter";
private Context context;
int resource;
    public Rechnung_Adapter(Context context, int resource,  List<Rechnung> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        String date = getItem(position).getDate();
        String euro = String.valueOf(getItem(position).getBetrag());
        String category = getItem(position).getCategory();
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvDate = (TextView) convertView.findViewById(R.id.list_item_date);
        TextView tvEuro = (TextView) convertView.findViewById(R.id.list_item_euro);
        TextView tvcate = (TextView) convertView.findViewById(R.id.list_item_category);

        tvDate.setText(date);
        tvEuro.setText(euro);
        tvcate.setText(category);

        return convertView;
    }
}

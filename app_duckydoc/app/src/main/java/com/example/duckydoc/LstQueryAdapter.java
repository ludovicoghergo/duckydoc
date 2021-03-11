package com.example.duckydoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LstQueryAdapter extends ArrayAdapter<String> {

    Context context;
    int resource;
    List<String> lstQuery;
    public LstQueryAdapter(Context context, int resource, List<String> lstQuery){
        super(context, resource, lstQuery);
        this.context = context;
        this.resource = resource;
        this.lstQuery = lstQuery;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource, null);

        //TextView lblUser = view.findViewById(R.id.lblUser);
        TextView lblText = view.findViewById(R.id.lblText);

        final String query = lstQuery.get(position);

        //lblUser.setText(query.getUser());
        lblText.setText(query);

        return view;
    }
}
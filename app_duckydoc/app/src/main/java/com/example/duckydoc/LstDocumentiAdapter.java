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

import com.example.duckydoc.DAO.Document;

import java.util.List;

public class LstDocumentiAdapter extends ArrayAdapter<Document> {

    Context context;
    int resource;
    List<Document> lstDocuments;
    public LstDocumentiAdapter(Context context, int resource, List<Document> lstDocument){
        super(context, resource, lstDocument);
        this.context = context;
        this.resource = resource;
        this.lstDocuments = lstDocument;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource, null);

        //Stampa valori nelle label
        TextView lblNome = view.findViewById(R.id.lblNomeDocumento);
        TextView lblUniversita = view.findViewById(R.id.lblUniversita);
        TextView lblCorso = view.findViewById(R.id.lblCorso);
        TextView lblAnno = view.findViewById(R.id.lblAnno);

        Document d = lstDocuments.get(position);

        lblNome.setText(d.getTitle());
        lblUniversita.setText(d.getUniversity());
        lblCorso.setText(d.getCourse());
        lblAnno.setText("Anno: " + d.getYear());

        return view;
    }

}
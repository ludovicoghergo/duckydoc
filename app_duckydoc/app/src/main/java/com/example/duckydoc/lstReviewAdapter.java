package com.example.duckydoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.duckydoc.DAO.Review;
import com.example.duckydoc.DAO.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class lstReviewAdapter extends ArrayAdapter<Review> {

    Context context;
    int resource;
    List<Review> lstReview;

    public lstReviewAdapter(Context context, int resource, List<Review> lstReview){
        super(context, resource, lstReview);
        this.context = context;
        this.resource = resource;
        this.lstReview = lstReview;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource, null);

        TextView lblUser = view.findViewById(R.id.lblUserReview);
        TextView lblData = view.findViewById(R.id.lblDataReview);
        TextView lblVote = view.findViewById(R.id.lblVotoReview);
        TextView lblText = view.findViewById(R.id.lblTextReview);

        Review review = lstReview.get(position);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = originalFormat.parse(String.valueOf(review.getData()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = newFormat.format(date);

        lblUser.setText(review.getUser().getUsername());
        lblData.setText("Data: " + formatedDate);
        lblVote.setText("Voto: " + String.valueOf(review.getVote()));
        lblText.setText(review.getText());

        return view;
    }
}
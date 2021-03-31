package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.duckydoc.DAO.Review;
import com.example.duckydoc.DAO.Tools;

import java.util.ArrayList;
import java.util.List;

public class SeeReviews extends AppCompatActivity {

    List<Review> lstReview = new ArrayList<>();
    lstReviewAdapter lstReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_reviews);
        setTitle("DuckyDoc - Reviews");

        lstReview = Tools.getDocumentReviews();

        ListView listViewStorico = findViewById(R.id.lstReviews);
        lstReviewAdapter = new lstReviewAdapter(this, R.layout.activity_lst_review_adapter, lstReview);
        listViewStorico.setAdapter(lstReviewAdapter);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();  // optional depending on your needs
    }
}
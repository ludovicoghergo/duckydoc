package com.example.duckydoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duckydoc.DAO.Review;
import com.example.duckydoc.DAO.Tools;
import com.example.duckydoc.DAO.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);
        setTitle("DuckyDoc");
    }

    public void btConferma_click(View view){
        if(!datiCorretti())
            return;

        Spinner sp = findViewById(R.id.cmbVoto);
        int voto = Integer.parseInt((String) sp.getSelectedItem());
        String text = ((EditText)findViewById(R.id.txtTextReview)).getText().toString();
        //Data attuale
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        int dataCreazione = Integer.parseInt(format.format(date));

        Review review = new Review(voto, text, dataCreazione, new User(Tools.account.getIdUser(), Tools.account.getName() + " " + Tools.account.getSurname()), Tools.document);

        //Create the input dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure to continue?");
        // Set up the buttons
        builder.setPositiveButton("Yes", (dialog, which) -> {
            if(!Tools.postReview(review)){
                error("Unable to send the review");
                return;
            }
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    public boolean datiCorretti(){
        boolean res = true;

        TextView lblVoto = findViewById(R.id.lblVotoErr);
        Spinner sp = findViewById(R.id.cmbVoto);
        try{
            int a = Integer.parseInt((String) sp.getSelectedItem());
            lblVoto.setText("");
        } catch (Exception e) {
            lblVoto.setText("Insert vote!");
            res = false;
        }

        TextView lblTesto = findViewById(R.id.lblTextRevErr);
        String descr = ((EditText)findViewById(R.id.txtTextReview)).getText().toString();
        if(descr.length() <= 0 || descr.length() > 255){
            lblTesto.setText("Insert description from 0 to 255 characters");
            res = false;
        }
        else{
            lblTesto.setText("");
        }

        return res;
    }

    public void error(String text){
        TextView lblError = findViewById(R.id.lblErrorReview);
        lblError.setTextColor(Color.RED);
        lblError.setText(text);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();  // optional depending on your needs
    }
}
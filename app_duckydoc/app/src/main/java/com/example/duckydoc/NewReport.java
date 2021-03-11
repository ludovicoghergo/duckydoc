package com.example.duckydoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duckydoc.DAO.Account;
import com.example.duckydoc.DAO.Answer;
import com.example.duckydoc.DAO.Query;
import com.example.duckydoc.DAO.Report;
import com.example.duckydoc.DAO.Tools;
import com.example.duckydoc.DAO.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        setTitle("DuckyDoc");
    }

    public void btConferma_Click(View view){
        error("");

        String testo = ((EditText)findViewById(R.id.txtTextReport)).getText().toString();
        //Testo non vuoto
        if(testo.length() < 1){
            error("Scrivere il motivo del report!");
            return;
        }

        Report report = new Report(Tools.document.getId(), testo, "In corso di valutazione", Tools.account);
        //Create the input dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sei sicuro di voler continuare?");
        // Set up the buttons
        builder.setPositiveButton("Si", (dialog, which) -> {
            if(!Tools.postReport(report)){
                error("Impossibile inviare il report");
                return;
            }
            onBackPressed();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void error(String text){
        TextView lblError = findViewById(R.id.lblReportErr);
        lblError.setTextColor(Color.RED);
        lblError.setText(text);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}
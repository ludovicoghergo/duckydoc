package com.example.duckydoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duckydoc.DAO.Answer;
import com.example.duckydoc.DAO.Query;
import com.example.duckydoc.DAO.Tools;
import com.example.duckydoc.DAO.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewQA extends AppCompatActivity {

    boolean query;
    String m;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_qa);

        query = getIntent().getBooleanExtra("NEW_QUERY_MESSAGE", false);

        TextView lblTitolo = findViewById(R.id.lblNewqa);
        if(query){
            lblTitolo.setText("Nuova domanda");
            m = "Confermi l'invio della domanda?";
        }
        else{
            lblTitolo.setText("Nuova risposta");
            m = "Confermi l'invio della risposta?";
        }
    }

    public void btCrea_Click(View view){
        error("");

        String testo = ((EditText)findViewById(R.id.txtTesto)).getText().toString();
        //Testo non vuoto
        if(testo.length() < 1){
            error("Scrivere la risposta!");
            return;
        }

        //Data attuale
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        int dataCreazione = Integer.parseInt(format.format(date));

        //Create the input dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(m);
        // Set up the buttons
        builder.setPositiveButton("Si", (dialog, which) -> {
            if(query){
                Query query = new Query(new User(Tools.account.getIdUser(), Tools.account.getName() + " " + Tools.account.getSurname()), testo, dataCreazione);
                if(!Tools.postQuery(query)){
                    error("Impossibile inviare la richiesta");
                    return;
                }
                onBackPressed();
            }
            else{
                Answer answer = new Answer(new User(Tools.account.getIdUser(), Tools.account.getName() + " " + Tools.account.getSurname()), testo, dataCreazione, false, Tools.query);
                if(!Tools.postAnswer(answer)){
                    error("Impossibile inviare la risposta");
                    return;
                }
                onBackPressed();
            }
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void error(String text){
        TextView lblError = findViewById(R.id.lblSendError);
        lblError.setTextColor(Color.RED);
        lblError.setText(text);
    }

    @Override
    public void onBackPressed()
    {
        if(!query) {
            Intent queryActivity = new Intent(this, SeeQuery.class);
            startActivity(queryActivity);
        }
        else{
            Intent queries = new Intent(this, CatalogoQuery.class);
            startActivity(queries);
        }
        finish();
    }
}
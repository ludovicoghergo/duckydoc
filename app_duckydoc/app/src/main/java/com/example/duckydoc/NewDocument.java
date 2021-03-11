package com.example.duckydoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duckydoc.DAO.Answer;
import com.example.duckydoc.DAO.Document;
import com.example.duckydoc.DAO.Query;
import com.example.duckydoc.DAO.Tools;
import com.example.duckydoc.DAO.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewDocument extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_document);
        setTitle("DuckyDoc - Nuovo documento");
    }

    public void btConferma_Click(View view){
        if(!datiCorretti()){
            return;
        }

        String titolo = ((EditText)findViewById(R.id.txtTitoloDocumento)).getText().toString();
        String universita = ((EditText)findViewById(R.id.txtUniversitaDocumento)).getText().toString();
        String corso = ((EditText)findViewById(R.id.txtCorsoDocumento)).getText().toString();
        //Anno
        Spinner sp = findViewById(R.id.cmbAnnoDocumento);
        int anno = Integer.parseInt((String) sp.getSelectedItem());

        int prezzo = Integer.parseInt(((EditText)findViewById(R.id.txtPrezzo)).getText().toString());
        String descrizione = ((EditText)findViewById(R.id.txtDescrizioneDocumento)).getText().toString();
        //Data attuale
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        int dataCreazione = Integer.parseInt(format.format(date));

        Document document = new Document(titolo, "pdf", dataCreazione, prezzo, descrizione, universita, anno, corso, "URL",
                new User(Tools.account.getIdUser(), Tools.account.getName() + " " + Tools.account.getSurname()));

        //Create the input dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sei sicuro di voler continuare?");
        // Set up the buttons
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!Tools.postDocument(document)){
                    error("Impossibile inviare il documento");
                    return;
                }
                Tools.lstDocuments.add(document);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private boolean datiCorretti(){
        boolean correct = true;

        TextView lblTitolo = findViewById(R.id.lblTitoloErr);
        String titolo = ((EditText)findViewById(R.id.txtTitoloDocumento)).getText().toString();
        if(titolo.equals("") || titolo.length() == 0){
            lblTitolo.setText("Inserire il titolo!");
            correct = false;
        }
        else{
            lblTitolo.setText("");
        }

        TextView lblUniversita = findViewById(R.id.lblUniversitaErr);
        String universita = ((EditText)findViewById(R.id.txtUniversitaDocumento)).getText().toString();
        if(universita.equals("") || universita.length() == 0){
            lblUniversita.setText("Inserire l'università di appartenenza!");
            correct = false;
        }
        else{
            lblUniversita.setText("");
        }

        TextView lblCorso = findViewById(R.id.lblCorsoErr);
        String corso = ((EditText)findViewById(R.id.txtCorsoDocumento)).getText().toString();
        if(corso.equals("") || corso.length() == 0){
            lblCorso.setText("Inserire il corso di appartenenza!");
            correct = false;
        }
        else{
            lblCorso.setText("");
        }

        TextView lblAnno = findViewById(R.id.lblAnnoErr);
        Spinner sp = findViewById(R.id.cmbAnnoDocumento);
        try{
            int a = Integer.parseInt((String) sp.getSelectedItem());
            lblAnno.setText("");
        } catch (Exception e) {
            lblAnno.setText("Inserire l'anno di frequenza!");
            correct = false;
        }

        TextView lblPrezzo = findViewById(R.id.lblPrezzoErr);
        String tmp = ((EditText)findViewById(R.id.txtPrezzo)).getText().toString();
        try{
            int p = Integer.parseInt(tmp);
            if(p <= 0){
                throw new Exception();
            }
            lblPrezzo.setText("");
        } catch (Exception e) {
            correct = false;
            lblPrezzo.setText("Inserire prezzo > 0!");
        }

        TextView lblDescrizione = findViewById(R.id.lblDescrizioneErr);
        String descr = ((EditText)findViewById(R.id.txtDescrizioneDocumento)).getText().toString();
        if(descr.length() <= 0 || descr.length() > 255){
            lblDescrizione.setText("Inserire descrizione tra 0 e 255 caratteri");
            correct = false;
        }
        else{
            lblDescrizione.setText("");
        }

        return correct;
    }

    public void error(String text){
        TextView lblError = findViewById(R.id.lblConfermaErr);
        lblError.setTextColor(Color.RED);
        lblError.setText(text);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();  // optional depending on your needs
    }
}
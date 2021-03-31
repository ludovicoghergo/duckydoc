package com.example.duckydoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.duckydoc.DAO.Account;
import com.example.duckydoc.DAO.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class SeeDocument extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_document);

        TextView lblTitolo = findViewById(R.id.lblTitoloDocumento);
        TextView lblUniversita = findViewById(R.id.lblUniversitaDocumento);
        TextView lblCorso = findViewById(R.id.lblCorsoDocumento);
        TextView lblAnno = findViewById(R.id.lblAnnoDocumento);
        TextView lblDataCreazione = findViewById(R.id.lblDataCreazione);
        TextView lblUtente = findViewById(R.id.lblUtenteDocumento);
        TextView lblDescrizione = findViewById(R.id.lblDescrizioneDocumento);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = originalFormat.parse(String.valueOf(Tools.document.getCreationData()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = newFormat.format(date);

        lblTitolo.setText(Tools.document.getTitle());
        lblUniversita.setText(Tools.document.getUniversity());
        lblCorso.setText("Course: " + Tools.document.getCourse());
        lblAnno.setText("Year: " + Tools.document.getYear());
        lblDataCreazione.setText("Upload date: " + formatedDate);
        lblUtente.setText("User: " + Tools.document.getUser().getUsername());
        lblDescrizione.setText(Tools.document.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //Menu options
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_seedocument, menu);
        if(Tools.document.getUser().getId() != Tools.account.getIdUser()) {
            MenuInflater inflater2 = getMenuInflater();
            inflater2.inflate(R.menu.menu_seedocument, menu);
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id) {
            case(R.id.seeReviews):
                startActivity(new Intent(this, SeeReviews.class));
                break;
            case(R.id.addReview):
                startActivity(new Intent(this, NewReview.class));
                break;
            case(R.id.addReport):
                startActivity(new Intent(this, NewReport.class));
                break;
        }
        return true;
    }

    public void btDownload_Click(View view) {
        //DOWNLOAD TOOL
        byte[] res = Tools.downloadDocument(Tools.document.getId());

        if(res != null){
            //Log.i("res", String.valueOf(res.length));
            //Log.i("path", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/"
                        + Tools.document.getNameFile());
                int cnt = 1;
                while (file.exists()) {
                    file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/copy"
                            + cnt + ")" + Tools.document.getNameFile());
                    cnt++;
                }
                if (!file.createNewFile())
                    return;
                String path = file.getPath();
                //Log.i("path", path);
                FileOutputStream stream = new FileOutputStream(path);
                stream.write(res);

                //Create the input dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Download completed!");
                // Set up the buttons
                builder.setNeutralButton("ok", (dialog, which) -> {
                    //Remove credits
                    int c = Tools.account.getCredits() - Tools.document.getPrice();
                    Tools.account.setCredits(c);
                    boolean b;
                    do {
                        b = Tools.putCredits(Tools.account.getIdUser(), c);
                    }while(!b);
                });
                builder.show();
            } catch (IOException e1) {
                //Log.i("Error", "error");
                e1.printStackTrace();
            }
        }
        else{
            Log.i("resError", "nullo");
        }
    }

    @Override
    public void onBackPressed()
    {
        Tools.document = null;
        super.onBackPressed();  // optional depending on your needs
    }
}
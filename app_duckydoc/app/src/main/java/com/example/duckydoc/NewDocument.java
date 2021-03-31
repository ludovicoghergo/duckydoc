package com.example.duckydoc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duckydoc.DAO.Account;
import com.example.duckydoc.DAO.Document;
import com.example.duckydoc.DAO.Tools;
import com.example.duckydoc.DAO.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewDocument extends AppCompatActivity {

    public final int RESULT_LOAD_FILE = 156;
    Uri selectedFile;
    TextView txtScegli;
    Intent i;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_document);
        setTitle("DuckyDoc - New document");

        txtScegli = findViewById(R.id.txtScegliFile);
        txtScegli.setText("No file selected");

        i = new Intent(this, CatalogoDocument.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void btConferma_Click(View view) throws IOException {
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
        File file = new File(selectedFile.getPath());
        String formatType;
        if(file.getPath().contains("image")){
            formatType = "image/png";
        }
        else{
            formatType = Files.probeContentType(file.toPath());
        }

        String name = file.getName();
        if(!name.contains(".")){
            if(name.contains("image")){
                name += ".png";
            }
        }

        Document document = new Document(titolo, name, formatType, dataCreazione,
                prezzo, descrizione, universita, anno, corso, new User(Tools.account.getIdUser(),
                Tools.account.getName() + " " + Tools.account.getSurname()));


        //Create the input dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure to continue?");
        // Set up the buttons
        builder.setPositiveButton("Yes", (dialog, which) -> {
            byte[] b = new byte[0];
            try {
                b = getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            document.setData(b);
            document.setSize((long) b.length);

            if(!Tools.postDocument(document)){
                error("Unable to send the document");
                return;
            }

            //Add credits
            int c = Tools.account.getCredits() + document.getPrice();
            Tools.account.setCredits(c);
            boolean tmp;
            do {
                tmp = Tools.putCredits(Tools.account.getIdUser(), c);
            }while(!tmp);

            Tools.lstDocuments.add(document);
            startActivity(i);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @SuppressLint("SetTextI18n")
    private boolean datiCorretti(){
        boolean correct = true;

        TextView lblTitolo = findViewById(R.id.lblTitoloErr);
        String titolo = ((EditText)findViewById(R.id.txtTitoloDocumento)).getText().toString();
        if(titolo.equals("") || titolo.length() == 0){
            lblTitolo.setText("Insert the title!");
            correct = false;
        }
        else{
            lblTitolo.setText("");
        }

        TextView lblUniversita = findViewById(R.id.lblUniversitaErr);
        String universita = ((EditText)findViewById(R.id.txtUniversitaDocumento)).getText().toString();
        if(universita.equals("") || universita.length() == 0){
            lblUniversita.setText("Insert the university!");
            correct = false;
        }
        else{
            lblUniversita.setText("");
        }

        TextView lblCorso = findViewById(R.id.lblCorsoErr);
        String corso = ((EditText)findViewById(R.id.txtCorsoDocumento)).getText().toString();
        if(corso.equals("") || corso.length() == 0){
            lblCorso.setText("Insert the course!");
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
            lblAnno.setText("Insert the attendance year!");
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
            lblPrezzo.setText("Insert price > 0!");
        }

        TextView lblDescrizione = findViewById(R.id.lblDescrizioneErr);
        String descr = ((EditText)findViewById(R.id.txtDescrizioneDocumento)).getText().toString();
        if(descr.length() <= 0 || descr.length() > 255){
            lblDescrizione.setText("Insert description from 0 to 255 characters");
            correct = false;
        }
        else{
            lblDescrizione.setText("");
        }

        if(selectedFile == null){
            txtScegli.setTextColor(Color.RED);
            correct = false;
        }

        return correct;
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void btScegli_Click(View view){
        //get a file
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");

        startActivityForResult(intent, RESULT_LOAD_FILE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_FILE && resultCode == RESULT_OK) {
            selectedFile = data.getData();

            Log.i("result", selectedFile.getPath());

            txtScegli.setTextColor(Color.BLACK);
            txtScegli.setText(selectedFile.getPath());
        }
    }

    public byte[] getBytes() throws IOException {
        InputStream iStream;
        try {
            iStream = getContentResolver().openInputStream(selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = iStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public void error(String text){
        TextView lblError = findViewById(R.id.lblConfermaErr);
        lblError.setTextColor(Color.RED);
        lblError.setText(text);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(i);
        finish();
    }
}
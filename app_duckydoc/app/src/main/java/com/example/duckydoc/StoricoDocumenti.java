package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.duckydoc.DAO.Document;
import com.example.duckydoc.DAO.Tools;

import java.util.ArrayList;
import java.util.List;

public class StoricoDocumenti extends AppCompatActivity {

    List<Document> lstDocument = new ArrayList<>();
    LstDocumentiAdapter lstDocumentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico_documenti);
        setTitle("DuckyDoc - Storico documenti");

        Intent documentActivity = new Intent(this, SeeDocument.class);

        lstDocument = Tools.getUserDocuments();
        Tools.lstDocuments = new ArrayList<>();
        Tools.lstDocuments.addAll(lstDocument);

        ListView listViewStorico = findViewById(R.id.lstStoricoDocumenti);
        lstDocumentAdapter = new LstDocumentiAdapter(this, R.layout.activity_lst_documenti_adapter, lstDocument);
        listViewStorico.setAdapter(lstDocumentAdapter);
        listViewStorico.setOnItemClickListener((parent, view, position, id) -> {
            Tools.document = lstDocument.get(position);
            startActivity(documentActivity);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //Menu options
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_storico_document,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id == R.id.filterStoricoDocument) {
            Intent filters = new Intent(this, DocumentFilter.class);
            startActivityForResult(filters, 155);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 155)
        {
            lstDocument.clear();
            lstDocument.addAll(Tools.lstDocuments);

            String titolo = data.getStringExtra("TITOLO_MESSAGE");
            if(titolo != null && !titolo.equals("")){
                for(int i = 0; i < lstDocument.size(); i++){
                    if(!lstDocument.get(i).getTitle().contains(titolo)){
                        lstDocument.remove(i);
                        i--;
                    }
                }
            }

            String uni = data.getStringExtra("UNIVERSITA_MESSAGE");
            if(uni != null && !uni.equals("")){
                for(int i = 0; i < lstDocument.size(); i++){
                    if(!lstDocument.get(i).getUniversity().contains(uni)){
                        lstDocument.remove(i);
                        i--;
                    }
                }
            }

            String corso = data.getStringExtra("CORSO_MESSAGE");
            if(corso != null && !corso.equals("")){
                for(int i = 0; i < lstDocument.size(); i++){
                    if(!lstDocument.get(i).getCourse().contains(corso)){
                        lstDocument.remove(i);
                        i--;
                    }
                }
            }

            int anno = data.getIntExtra("ANNO_MESSAGE", -1);
            if(anno != -1){
                for(int i = 0; i < lstDocument.size(); i++){
                    if(!(lstDocument.get(i).getYear() == anno)){
                        lstDocument.remove(i);
                        i--;
                    }
                }
            }
        }
        ListView listViewStorico = findViewById(R.id.lstStoricoDocumenti);
        lstDocumentAdapter = new LstDocumentiAdapter(this, R.layout.activity_lst_documenti_adapter, lstDocument);
        listViewStorico.setAdapter(lstDocumentAdapter);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, Home.class));
        super.onBackPressed();  // optional depending on your needs
    }
}
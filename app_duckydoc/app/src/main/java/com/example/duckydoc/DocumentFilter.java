package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class DocumentFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_filter);
        setTitle("Filters");
    }

    public void btMostra_Click(View view){
        Intent intent=new Intent();

        EditText txtTitolo = findViewById(R.id.txtFiltroTitolo);
        String titolo = txtTitolo.getText().toString();
        if(titolo != null){
            intent.putExtra("TITOLO_MESSAGE", titolo);
        }

        EditText txtUni = findViewById(R.id.txtFiltroUniversita);
        String uni = txtUni.getText().toString();
        if(uni != null){
            intent.putExtra("UNIVERSITA_MESSAGE", uni);
        }

        EditText txtCorso = findViewById(R.id.txtFiltroCorso);
        String corso = txtCorso.getText().toString();
        if(corso != null){
            intent.putExtra("CORSO_MESSAGE", corso);
        }

        Spinner sp = findViewById(R.id.cmbFiltroAnno);
        try{
            int a = Integer.parseInt((String) sp.getSelectedItem());
            intent.putExtra("ANNO_MESSAGE", a);
        } catch (Exception ignored) {

        }

        setResult(155,intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent;
        if(getIntent().getBooleanExtra("CATALOGO_MESSAGE", false)) {
            intent = new Intent(this, CatalogoDocument.class);
        }
        else{
            intent = new Intent(this, StoricoDocumenti.class);
        }
        startActivity(intent);
        super.onBackPressed();  // optional depending on your needs
    }
}
package com.example.duckydoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.duckydoc.DAO.Tools;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("DuckyDoc");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //Menu options
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                finish();
                break;
            case R.id.storicoDocumenti:
                startActivity(new Intent(this, StoricoDocumenti.class));
                finish();
                break;
            case R.id.storicoQuery:
                startActivity(new Intent(this, StoricoQuery.class));
                finish();
                break;
            case R.id.storicoAnswer:
                startActivity(new Intent(this, StoricoAnswer.class));
                finish();
                break;
        }
        return false;
    }

    public void btDocuments_Click(View view){
        startActivity(new Intent(this, CatalogoDocument.class));
        finish();
    }

    public void btQueries_Click(View view){
        startActivity(new Intent(this, CatalogoQuery.class));
        finish();
    }
}
package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.duckydoc.DAO.Query;
import com.example.duckydoc.DAO.Tools;

import java.util.ArrayList;
import java.util.List;

public class CatalogoQuery extends AppCompatActivity {

    public List<Query> lstQuery = new ArrayList<>();
    LstQueryAdapter lstQueryAdapter;
    public List<String> lstString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo_query);
        setTitle("DuckyDoc - Q&A");

        Intent queryActivity = new Intent(this, SeeQuery.class);

        lstQuery = Tools.getQueries();
        lstString = new ArrayList<>();
        for(Query q : lstQuery){
            lstString.add(q.getText());
        }

        ListView listViewCatalogo = findViewById(R.id.lstCatalogoQuery);
        lstQueryAdapter = new LstQueryAdapter(this, R.layout.lst_query_adapter, lstString);
        listViewCatalogo.setAdapter(lstQueryAdapter);
        listViewCatalogo.setOnItemClickListener((parent, view, position, id) -> {
            queryActivity.putExtra("QUERY_MESSAGE", lstQuery.get(position).getId());
            startActivity(queryActivity);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //Menu options
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_qa,menu);

        MenuItem menuItem = menu.findItem(R.id.qa_search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                lstString.clear();
                for(Query q : lstQuery){
                    lstString.add(q.getText());
                }

                for(int i = 0; i < lstString.size(); i++){
                    if(!lstString.get(i).contains(s) && !lstString.get(i).toLowerCase().contains(s)){
                        lstString.remove(i);
                        i--;
                    }
                }
                return true;
            }
        });

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id == R.id.newQuery) {
            Intent newQuery = new Intent(this, NewQA.class);
            newQuery.putExtra("NEW_QUERY_MESSAGE", true);
            startActivity(newQuery);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, Home.class));
        super.onBackPressed();  // optional depending on your needs
    }
}
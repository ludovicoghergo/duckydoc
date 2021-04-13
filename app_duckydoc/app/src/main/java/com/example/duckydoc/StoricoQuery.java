package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.duckydoc.DAO.Query;
import com.example.duckydoc.DAO.Tools;

import java.util.ArrayList;
import java.util.List;

public class StoricoQuery extends AppCompatActivity {

    List<Query> lstQuery = new ArrayList<>();
    LstQueryAdapter lstQueryAdapter;
    List<String> lstString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico_query);
        setTitle("DuckyDoc - Questions history");

        Intent queryActivity = new Intent(this, SeeQuery.class);

        lstQuery = Tools.getUserQueries();
        lstString = new ArrayList<>();
        for(Query q : lstQuery){
            lstString.add(q.getTitle());
        }

        ListView listViewStorico = findViewById(R.id.lstStoricoQuery);
        lstQueryAdapter = new LstQueryAdapter(this, R.layout.lst_query_adapter, lstString);
        listViewStorico.setAdapter(lstQueryAdapter);
        listViewStorico.setOnItemClickListener((parent, view, position, id) -> {
            queryActivity.putExtra("QUERY_MESSAGE", lstQuery.get(position).getId());
            startActivity(queryActivity);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //Menu options
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_storico_qa,menu);

        MenuItem menuItem = menu.findItem(R.id.storico_qa_search);
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
                    lstString.add(q.getTitle());
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

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, Home.class));
        super.onBackPressed();  // optional depending on your needs
    }
}
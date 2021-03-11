package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.duckydoc.DAO.Query;
import com.example.duckydoc.DAO.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeeQuery extends AppCompatActivity {

    LstAnswerQueryAdapter lstAnswerAdapter;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_query);

        //get the query
        if(Tools.query == null) {
            long id = getIntent().getLongExtra("QUERY_MESSAGE", 0);
            query = Tools.getQueriesId(id);
            Tools.query = query;
        }
        else{
            query = Tools.query;
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = originalFormat.parse(String.valueOf(Tools.query.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        assert date != null;
        String formatedDate = newFormat.format(date);

        TextView lblUser = findViewById(R.id.lblUserQuery);
        TextView lblText = findViewById(R.id.lblUserText);
        TextView lblData = findViewById(R.id.lblDataQuery);

        lblUser.setText(query.getUser().getUsername());
        lblText.setText(query.getText());
        lblData.setText(formatedDate);

        ListView listViewAnswer = findViewById(R.id.lstAnswers);
        lstAnswerAdapter = new LstAnswerQueryAdapter(this, R.layout.activity_lst_answer_query_adapter, Tools.getQueryAnswers(Tools.query.getId()));
        listViewAnswer.setAdapter(lstAnswerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //Menu options
    {
        if(Tools.query.getUser().getId() != Tools.account.getIdUser()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_see_query, menu);
            return true;
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if (id == R.id.reply_query) {
            Intent newAnswer = new Intent(this, NewQA.class);
            newAnswer.putExtra("NEW_QUERY_MESSAGE", false);
            startActivity(newAnswer);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Tools.query = null;
        finish();
    }
}
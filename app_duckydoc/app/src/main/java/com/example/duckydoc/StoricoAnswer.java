package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.duckydoc.DAO.Answer;
import com.example.duckydoc.DAO.Tools;

import java.util.ArrayList;
import java.util.List;

public class StoricoAnswer extends AppCompatActivity {

    List<Answer> lstAnswer = new ArrayList<>();
    LstAnswerAdapter lstAnswerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico_answer);
        setTitle("DuckyDoc - Storico risposte");

        Intent queryActivity = new Intent(this, SeeQuery.class);

        lstAnswer = Tools.getUserAnswers();
        ListView listViewStorico = findViewById(R.id.lstStoricoAnswer);
        lstAnswerAdapter = new LstAnswerAdapter(this, R.layout.lst_answer_adapter, lstAnswer);
        listViewStorico.setAdapter(lstAnswerAdapter);
        listViewStorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                queryActivity.putExtra("QUERY_MESSAGE", lstAnswer.get(position).getQuery().getId());
                startActivity(queryActivity);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, Home.class));
        super.onBackPressed();  // optional depending on your needs
    }
}
package com.example.duckydoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.duckydoc.DAO.Account;
import com.example.duckydoc.DAO.Answer;
import com.example.duckydoc.DAO.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LstAnswerQueryAdapter extends ArrayAdapter<Answer> {

    Context context;
    int resource;
    List<Answer> lstAnswers;

    public LstAnswerQueryAdapter(Context context, int resource, List<Answer> lstAnswers){
        super(context, resource, lstAnswers);
        this.context = context;
        this.resource = resource;
        this.lstAnswers = lstAnswers;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(resource, null);

        TextView lblUser = view.findViewById(R.id.lblUserAnswer);
        TextView lblAnswer = view.findViewById(R.id.lblAnswer);
        TextView lblData = view.findViewById(R.id.lblDataAnswer);
        CheckBox chkCorrect = view.findViewById(R.id.chkCorrectAnswer);
        Button btCorretta = view.findViewById(R.id.btAnswerCorrect);

        final Answer answer = lstAnswers.get(position);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = originalFormat.parse(String.valueOf(answer.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
        assert date != null;
        String formatedDate = newFormat.format(date);

        lblUser.setText(answer.getUser().getUsername());
        lblAnswer.setText(answer.getText());
        lblData.setText(formatedDate);
        chkCorrect.setChecked(answer.isCorrect());
        if(answer.isCorrect() || answer.getQuery().getUser().getId() != Tools.account.getIdUser()){
            btCorretta.setVisibility(View.GONE);
        }

        btCorretta.setOnClickListener(v -> {
            //Create the input dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Are you sure that the Answer is correct?");
            // Set up the buttons
            builder.setPositiveButton("Yes", (dialog, which) -> {
                if(Tools.putCorrect(answer.getId())){
                    answer.setCorrect(true);
                    chkCorrect.setChecked(true);
                    btCorretta.setVisibility(View.GONE);

                    //Add credits
                    Account a;
                    Log.i("info", String.valueOf(answer.getUser().getId()));
                    do {
                        a = Tools.getUser(answer.getUser().getId());
                        //Log.i("info", a.toString());
                    }while(a == null);
                    int c = a.getCredits() + 20;
                    boolean b;
                    do {
                        b = Tools.putCredits(a.getIdUser(), c);
                    }while(!b);
                }
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            builder.show();
        });

        return view;
    }
}
package com.example.duckydoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.duckydoc.DAO.Answer;

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
        View view = inflater.inflate(resource, null);

        TextView lblUser = view.findViewById(R.id.lblUserAnswer);
        TextView lblAnswer = view.findViewById(R.id.lblAnswer);
        TextView lblData = view.findViewById(R.id.lblDataAnswer);
        CheckBox chkCorrect = view.findViewById(R.id.chkCorrectAnswer);

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
        Log.i("correct", String.valueOf(answer.isCorrect()));
        chkCorrect.setChecked(answer.isCorrect());

        return view;
    }
}
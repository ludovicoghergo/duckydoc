package com.example.duckydoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duckydoc.DAO.Tools;
import com.example.duckydoc.DAO.Account;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("DuckyDoc");

        String s = Tools.getSharedData(this, Tools.sUserKey);

        if((!s.equals(null) && !s.equals(""))){
            Gson gson = new Gson();
            Tools.account = gson.fromJson(s, Account.class);

            /*User tmp = Tools.loginUser(Tools.user.getEmail(), Tools.user.getPassword());
            if(tmp == null){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Errore");
                alertDialogBuilder.setMessage("Si è verificato un problema nella comunazione con il server");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK", (dialog, id) -> dialog.cancel());
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return;
            }*/
            startActivity(new Intent(this, Home.class));
            finish();
        }
    }

    public void btLogin_Click(View view){
        Tools.setErrorMessage(findViewById(R.id.lblLogin), "");

        if(!datiCorretti())
            return;

        String email = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
        String psw = ((EditText)findViewById(R.id.txtPassword)).getText().toString();
        //User user = new User(1, email, "temp", psw);

        //Richiesta di login
        Account account = Tools.loginUser(email, psw);
        if(account == null){
            Tools.setErrorMessage(findViewById(R.id.lblLogin), Tools.getSqlErrorContainer());
        }
        else{
            Gson gson = new Gson();
            Tools.saveSharedData(this, Tools.sUserKey, gson.toJson(account));
            Tools.account = account;
            startActivity(new Intent(this, Home.class));
            finish();
        }
    }

    boolean datiCorretti(){
        boolean result = true;
        String email = ((EditText)findViewById(R.id.txtEmail)).getText().toString();
        TextView lblEmail = findViewById(R.id.lblEmail);
        if(email.equals("") || email.equals(null) || !email.contains("@") || !email.contains(".") || email.length() < 5){
            Tools.setErrorMessage(lblEmail, "Email inserita non valida");
            result = false;
        }
        else{
            lblEmail.setText("");
        }

        String psw = ((EditText)findViewById(R.id.txtPassword)).getText().toString();
        TextView lblPsw = findViewById(R.id.lblPassword);
        if(psw.equals("") || psw.equals(null)){
            Tools.setErrorMessage(lblPsw, "Nessuna password inserita");
            result = false;
        }
        else if(psw.length() < 6 || psw.length() > 20){
            Tools.setErrorMessage(lblPsw, "La password deve contenere tra gli 6 e i 20 caratteri");
            result = false;
        }
        else{
            lblPsw.setText("");
        }
        return  result;
    }
}
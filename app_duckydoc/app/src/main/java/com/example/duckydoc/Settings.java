package com.example.duckydoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duckydoc.DAO.Tools;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Settings extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("DuckyDoc - Settings");

        TextView lblCoins = findViewById(R.id.lblCoins);
        lblCoins.setText("Current credits: " + Tools.account.getCredits());
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, Home.class));
        super.onBackPressed();  // optional depending on your needs
    }

    public void btLogout_Click(View view) {
        Intent i = new Intent(this, MainActivity.class);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    // ...
                    startActivity(i);
                    finish();
                });
    }
}
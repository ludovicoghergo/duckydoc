package com.example.duckydoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duckydoc.DAO.Tools;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("DuckyDoc - Impostazioni");
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, Home.class));
        super.onBackPressed();  // optional depending on your needs
    }

    public void btLogout_Click(View view) {
        Tools.saveSharedData(this, Tools.sUserKey, "");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
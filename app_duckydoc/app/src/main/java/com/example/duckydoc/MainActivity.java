package com.example.duckydoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.duckydoc.DAO.Account;
import com.example.duckydoc.DAO.Tools;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("DuckyDoc");

        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE }, 1);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("841652059553-9hr5n9nn3qbasshhp02faa8sbikuc18a.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Account a = Tools.loginUser(account.getId());
            if(a == null){
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, task -> {
                            // ...
                        });
            }
            else{
                Tools.account = a;
                startActivity(new Intent(this, Home.class));
                finish();
            }
        }
    }

    public void btLoginGoogle_Click(){
        TextView lbl = findViewById(R.id.lblLogin);
        lbl.setText("");

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            if(!handleSignInResult(task)){
                TextView lbl = findViewById(R.id.lblLogin);
                lbl.setTextColor(Color.RED);
                lbl.setText("Login failed");
                return;
            }

            startActivity(new Intent(this, Home.class));
            finish();
        }
    }

    private boolean handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account != null){
                Account a = Tools.loginUser(account.getId());
                if(a == null){
                    a = new Account(account.getId(), account.getEmail(), account.getGivenName(), account.getFamilyName(), 100);
                    Tools.account = a;
                    return Tools.postUser(a);
                }
                else{
                    Tools.account = a;
                    return true;
                }
            }
            return false;
            //Log.i("utente", account.getDisplayName() + ", " + account.getGivenName() + ", " + account.getFamilyName() + ", " + account.getId() + ", " + account.getIdToken());

        } catch (ApiException e) {
            Log.i("TAG", "signInResult:failed code=" + e.getStatusCode());
            return false;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                btLoginGoogle_Click();
                break;
        }
    }
}
package com.example.duckydoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Account a = Tools.loginUser(account.getId());
            if(a == null){
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                            }
                        });
            }
            else{
                //Tools.account = a;
                Tools.account = Tools.loginUser(account.getId());
                Log.i("INFO", String.valueOf(Tools.account.getIdUser()));
                startActivity(new Intent(this, Home.class));
                finish();
            }
        }
        // [END on_start_sign_in]
    }

    public void btLoginGoogle_Click(){
        TextView lbl = findViewById(R.id.lblLogin);
        lbl.setText("");

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

            startActivity(new Intent(this, Home.class));
            finish();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account != null){
                Account a = Tools.loginUser(account.getId());
                if(a == null){
                    a = new Account(account.getId(), account.getEmail(), account.getGivenName(), account.getFamilyName());
                    Tools.account = a;
                    if(!Tools.postUser(a)){
                        TextView lbl = findViewById(R.id.lblLogin);
                        lbl.setTextColor(Color.RED);
                        lbl.setText("Login non eseguito");
                        return;
                    }
                }
                else{
                    Tools.account = a;
                }
                startActivity(new Intent(this, Home.class));
                finish();
            }
            Log.i("utente", account.getDisplayName() + ", " + account.getGivenName() + ", " + account.getFamilyName() + ", " + account.getId() + ", " + account.getIdToken());

        } catch (ApiException e) {
            Log.i("TAG", "signInResult:failed code=" + e.getStatusCode());
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
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
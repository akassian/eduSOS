package com.example.edusos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LogInSignUpActivity extends AppCompatActivity {
    // For Sign in
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 007;
    GoogleSignInAccount googleAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_up);
    }
    public void onBackClick(View button) {
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    // For Sign in
    public void onLoginClick(View button) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        signIn();
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent,1);
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
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("SIGNIN_!: ", account.getDisplayName() + ",   " + account.getEmail());

            ((EduSOSApplication) LogInSignUpActivity.this.getApplication()).setAccount(account); // set global variable account


            //String personName = account.getDisplayName();

//            if (account.getPhotoUrl()!= null) {
//                String personPhotoUrl = account.getPhotoUrl().toString();
//            }

            //String email = account.getEmail();

            //txt_welcome.setText("Welcome "+personName+"\n"+ email);

            updateUI(account);   // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("SIGNIN_", "signInResult:failed code=" + e.getStatusCode());
            Log.d("SIGNIN_", "signInResult:failed code=" + e.getStatusCode());

            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        // For example, display Welcome name if account is not null
        if (account != null) {
            //Toast.makeText(this, account.getDisplayName(), Toast. LENGTH_LONG).show();
            Log.d("SIGNIN_UI", account.getDisplayName() + ",   " + account.getEmail());
        } else {
            Log.d("SIGNIN_UI", "Sign in error!");
        }

    }
}

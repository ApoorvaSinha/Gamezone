package com.example.gamezone;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends  AppCompatActivity {
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private  String TAG = "login";
    private FirebaseAuth mAuth;
    private Button btnSignOut;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        mAuth = FirebaseAuth.getInstance();
        btnSignOut = findViewById(R.id.sign_out_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Toast.makeText(Login.this,"You are Logged Out",Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(Login.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            gotoProfile();
            btnSignOut.setVisibility(View.VISIBLE);
            //FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(Login.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            //FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(Login.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser fUser){
        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Toast.makeText(Login.this,personName + personEmail ,Toast.LENGTH_SHORT).show();
        }

    }
    private void gotoProfile(){
        Intent intent=new Intent(Login.this,MainActivity.class);
        startActivity(intent);
    }

}
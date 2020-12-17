package com.example.week6labcarrental.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.controller.Authentication;
import com.example.week6labcarrental.controller.PopUp;
import com.example.week6labcarrental.firebase.UserCollection;
import com.example.week6labcarrental.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView createAccount;
    Dialog dialog;
    ProgressDialog progressDialog;
    Button logIn;
    EditText email, password;
    // Firebase auth
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    BroadcastReceiver response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialize firebase store
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        dialog = new Dialog(this);
        progressDialog = new ProgressDialog(this);
        // find resource id

        createAccount = findViewById(R.id.createAccount);
        createAccount.setOnClickListener(this);
        logIn = findViewById(R.id.login);
        logIn.setOnClickListener(this);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        //initialize broadcast receiver
        response = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(UserCollection.LOAD_USER_DATA_DONE)) {
                    //get user Data from intent
                    User userData = (User) intent.getSerializableExtra("user_data");
                    // process the data
                    Authentication.processUserData(context, userData);
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            // get user data
            UserCollection.getUserInformation(LoginActivity.this, db, currentUser.getUid());
        }
    }
    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(response);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter(UserCollection.LOAD_USER_DATA_DONE);
        // register broadcast
        registerReceiver(response,filter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAccount:
                PopUp.openRegisterPopup(db, dialog, LoginActivity.this, progressDialog, mAuth);
                break;
            case R.id.login:
                progressDialog.setMessage("Login...");
                progressDialog.show();
                String em = email.getText().toString();
                String pw = password.getText().toString();
                Authentication.signIn(this,db, mAuth, progressDialog, em, pw);
                break;
        }
    }
}

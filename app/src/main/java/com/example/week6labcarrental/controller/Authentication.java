package com.example.week6labcarrental.controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.week6labcarrental.firebase.UserCollection;
import com.example.week6labcarrental.model.User;
import com.example.week6labcarrental.ui.ClientActivity;
import com.example.week6labcarrental.ui.LoginActivity;
import com.example.week6labcarrental.ui.ManagerActivity;
import com.example.week6labcarrental.ui.SaleActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Authentication {
    /**
     * This function register new user
     * @param dialog
     * @param db
     * @param context
     * @param mAuth
     * @param progressDialog
     * @param email
     * @param password
     * @param fullName
     */
    public static void registerNewUser(final Dialog dialog, final FirebaseFirestore db, final Context context, final FirebaseAuth mAuth, final ProgressDialog progressDialog, final String email, String password, final String fullName) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser userInfo = mAuth.getCurrentUser();
                            // Create new user object
                            // Set default role to 1 (User Role)
                            User user= new User(userInfo.getUid(), fullName, email, 1);
                            UserCollection.addNewUserToFirebase(db, user, userInfo.getUid());

                            progressDialog.hide();
                            dialog.dismiss();
                            Toast.makeText(context, "Successfully register!", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    /**
     * This function sign in user with email and password
     *
     * @param context
     * @param db
     * @param mAuth
     * @param progressDialog
     * @param email
     * @param password
     */
    public static void signIn(final Context context, final FirebaseFirestore db, final FirebaseAuth mAuth, final ProgressDialog progressDialog, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        progressDialog.hide();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserCollection.getUserInformation(context, db, user.getUid());

                          //  ((Activity) context).finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    /**
     * This function process the user data and take the user to the appropriate activity
     * @param context
     * @param newUser
     */
    public static void processUserData(Context context, User newUser) {
        Intent intent;
        switch (newUser.getRole()) {
            case 1:
                Log.i("role", "Client");
                intent = new Intent(context, ClientActivity.class);
                context.startActivity(intent);
                break;
            case 2:
                Log.i("role", "Sale");
                intent = new Intent(context, SaleActivity.class);
                context.startActivity(intent);
                break;
            case 3:
                Log.i("role", "Manager");
                intent = new Intent(context, ManagerActivity.class);
                context.startActivity(intent);
                break;
            default:
                Log.i("role", "default");
                break;
        }
    }

    /**
     *
     * @param mAuth
     * @param context
     */
    public static void checkSignIn(FirebaseAuth mAuth, Context context) {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }
}

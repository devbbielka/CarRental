package com.example.week6labcarrental.firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.week6labcarrental.model.Car;
import com.example.week6labcarrental.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserCollection {
    public static final String LOAD_USER_DATA_DONE = "loadUserDataDone";
    public static final String LOAD_USER_LIST_DATA_DONE = "loadUserListDataDone";

    public static final String COLLECTION_NAME = "users";
    /**
     * This function add a new user to User Collection
     * @param db  db reference
     * @param user user info
     * @param userId user id is user for Document name
     */
    public static void addNewUserToFirebase(FirebaseFirestore db, User user, String userId) {
        // Add a new document with a generated ID
        db.collection(COLLECTION_NAME)
                .document(userId)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add User", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Log.w("Add User", "Error writing document", e);
                    }
                });
    }

    /**
     * Get user information, broadcast user info when finish
     * @param db db reference
     * @param documentId user document ID
     */
    public static void getUserInformation(final Context context, FirebaseFirestore db, String documentId) {
        final User user = new User();
        DocumentReference userRef = db.collection(COLLECTION_NAME).document(documentId);
        userRef.get()
                .addOnCompleteListener(new OnCompleteListener <DocumentSnapshot> () {
                    @Override
                    public void onComplete( Task < DocumentSnapshot > task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();

                            user.setEmail(doc.getString("email"));
                            user.setFullName(doc.getString("fullName"));
                            user.setUserId(doc.getString("userId"));
                            user.setRole(doc.getLong("role").intValue());

                        }
                        //Broadcast the result
                        Intent broadcast = new Intent();

                        broadcast.setAction(LOAD_USER_DATA_DONE);
                        broadcast.putExtra("user_data",user);

                        context.sendBroadcast(broadcast);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                    }
                });

    }

    /**
     * This function get all users in the users collection
     * @param context context for broadcast
     * @param db db instance
     */
    public static void getAllUsers(final Context context, FirebaseFirestore db) {
        final ArrayList<User> users = new ArrayList<>();
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                User user = new User();
                                Log.d("load doc", doc.getId() + " => " + doc.getData());
                                user.setUserId(doc.getString("userId"));
                                user.setEmail(doc.getString("email"));
                                user.setFullName(doc.getString("fullName"));
                                user.setRole(doc.getLong("role").intValue());
                                //add to list
                                users.add(user);
                            }
                        } else {
                            Log.w("load doc", "Error getting documents.", task.getException());
                        }
                        //Broadcast the result
                        Intent broadcast = new Intent();

                        broadcast.setAction(LOAD_USER_LIST_DATA_DONE);
                        broadcast.putExtra("users_data",users);

                        context.sendBroadcast(broadcast);
                    }
                });

    }
}

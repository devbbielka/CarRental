package com.example.week6labcarrental.firebase;

import android.util.Log;

import com.example.week6labcarrental.model.Booking;
import com.example.week6labcarrental.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookingCollection {
    public static final String COLLECTION_NAME = "bookings";

    public static void addBooking(FirebaseFirestore db, Booking booking, String id) {

        // Add a new document with a generated ID
        db.collection(COLLECTION_NAME)
                .document(id)
                .set(booking)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Add Booking", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Log.w("Add Booking", "Error writing document", e);
                    }
                });
    }
}

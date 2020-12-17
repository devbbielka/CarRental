package com.example.week6labcarrental.firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.week6labcarrental.model.Car;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CarCollection {
    public static final String LOAD_CAR_DATA_DONE = "doneLoadCarData";
    public static final String ADD_CAR_DATA_DONE = "doneAddCarData";
    public static final String UPDATE_CAR_DATA_DONE = "doneUpdateCarData";
    public static final String COLLECTION_NAME = "cars";

    /**
     *
     * @param db firebase instance
     * @param car car object to be saved
     */
    public static void createAndUpdateCar(final Context context, FirebaseFirestore db, final Car car, final boolean isAddNew) {
                db.collection(COLLECTION_NAME)
                .document(car.getCarId())
                .set(car)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Car ", "DocumentSnapshot successfully written!");
                        Intent intent = new Intent();
                        if(isAddNew) {
                            intent.setAction(ADD_CAR_DATA_DONE);
                            intent.putExtra("new_car_data", car);
                        } else {
                            intent.setAction(UPDATE_CAR_DATA_DONE);
                        }
                        context.sendBroadcast(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Log.w("Car", "Error writing document", e);
                    }
                });
    }

    /**
     *
     * @param context for broadcast
     * @param db Firebase store
     * @return List of cars in the DB
     */
    public static ArrayList<Car> getAllCars(final Context context, FirebaseFirestore db) {
        final ArrayList<Car> cars = new ArrayList<>();
        db.collection(COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Car car = new Car();
                                Log.d("load doc", doc.getId() + " => " + doc.getData());
                                car.setCategory(doc.getString("category"));
                                car.setCarModel(doc.getString("carModel"));
                                car.setCarMake(doc.getString("carMake"));
                                car.setColor(doc.getString("color"));
                                car.setPricePerDay(doc.getLong("pricePerDay"));
                                car.setPricePerHour(doc.getLong("pricePerHour"));
                                car.setAvailibality(doc.getBoolean("availability"));
                                car.setCarId(doc.getString("carId"));
                                car.setSeats(doc.getLong("seats").intValue());
                                //add car to list
                                cars.add(car);
                            }
                        } else {
                            Log.w("load doc", "Error getting documents.", task.getException());
                        }
                        //Broadcast the result
                        Intent broadcast = new Intent();

                        broadcast.setAction(LOAD_CAR_DATA_DONE);
                        broadcast.putExtra("cars_data",cars);

                        context.sendBroadcast(broadcast);
                    }
                });
        return cars;
    }
}

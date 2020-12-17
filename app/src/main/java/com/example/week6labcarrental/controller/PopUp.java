package com.example.week6labcarrental.controller;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.firebase.CarCollection;
import com.example.week6labcarrental.model.Car;
import com.example.week6labcarrental.ui.ManagerActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PopUp {
    /**
     * This function open a popup for user to register a new account
     *
     * @param dialog
     * @param context
     * @param progressDialog
     * @param mAuth
     */
    public static void openRegisterPopup(final FirebaseFirestore db, final Dialog dialog, final Context context, final ProgressDialog progressDialog, final FirebaseAuth mAuth) {

        dialog.setContentView(R.layout.register_popup);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView closeBtn = dialog.findViewById(R.id.btnClose);
        final EditText email = dialog.findViewById(R.id.txtEmail);
        final EditText fullName = dialog.findViewById(R.id.txtFullName);
        final EditText txtpassword = dialog.findViewById(R.id.txtPassword);
        final EditText confirmPassword = dialog.findViewById(R.id.txtConfirmPassword);
        final Button btnSignUp = dialog.findViewById(R.id.btnRegister);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = fullName.getText().toString().trim();
                String emailString = email.getText().toString().trim();
                String pw = txtpassword.getText().toString().trim();
                String pw1 = confirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(emailString)) {
                    Toast.makeText(context, "Please fill up email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(context, "Please fill up name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    Toast.makeText(context, "Please fill up password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pw1)) {
                    Toast.makeText(context, "Please fill up confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pw.equals(pw1)) {
                    Toast.makeText(context, "Password doesn't match. Try Again", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Register...");
                progressDialog.show();
                //Register new user
                Authentication.registerNewUser(dialog, db, context, mAuth, progressDialog, emailString, pw, userName);

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void openAddNewCarPopup(final Context context, final FirebaseFirestore db, final Dialog dialog) {

        dialog.setContentView(R.layout.add_new_car_popup);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView closeBtn = dialog.findViewById(R.id.btnClose);

        final String ID = UUID.randomUUID().toString();
        final Spinner carCategoryEdit = dialog.findViewById(R.id.carCategoryEdit);
        final EditText carPriceHourEdit = dialog.findViewById(R.id.carPriceHourEdit);
        final EditText carPriceDayEdit = dialog.findViewById(R.id.carPriceDayEdit);
        final EditText carMakerEdit = dialog.findViewById(R.id.carMakerEdit);
        final EditText carModelEdit = dialog.findViewById(R.id.carModelEdit);
        final EditText carColorEdit = dialog.findViewById(R.id.carColorEdit);
        final CheckBox carAvailable = dialog.findViewById(R.id.carAvailableCheckBox);
        final EditText carSeats = dialog.findViewById(R.id.carSeats);
        carAvailable.setChecked(true);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.car_category_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        carCategoryEdit.setAdapter(adapter);

        final Button addnew = dialog.findViewById(R.id.btnAddNew);
        Button update = dialog.findViewById(R.id.btnUpdate);
        update.setVisibility(View.GONE);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carCategoryEdit.getSelectedItemPosition()==0){
                    Toast.makeText(context, "Please select a category.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carMakerEdit.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter a car maker.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carModelEdit.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter a car model.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carColorEdit.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter a car color.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carPriceDayEdit.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter a daily price.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carPriceHourEdit.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter a hourly price.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carSeats.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter car seats.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Car car = new Car();
                car.setCarId(ID);
                car.setCarMake(carMakerEdit.getText().toString());
                car.setCarModel(carModelEdit.getText().toString());
                car.setCategory(carCategoryEdit.getSelectedItem().toString());
                car.setColor(carColorEdit.getText().toString());
                car.setPricePerDay(Double.parseDouble(carPriceDayEdit.getText().toString()));
                car.setPricePerHour(Double.parseDouble(carPriceHourEdit.getText().toString()));
                car.setSeats(Integer.parseInt(carSeats.getText().toString()));
                car.setAvailibality(carAvailable.isChecked());
                // call the add new function from CarCollection
                CarCollection.createAndUpdateCar(context, db, car, true);
                //hide the dialog
                dialog.dismiss();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void openUpdateCarPopup(final Context context, final FirebaseFirestore db, final Dialog dialog, final Car car) {
        dialog.setContentView(R.layout.add_new_car_popup);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView closeBtn = dialog.findViewById(R.id.btnClose);

        final Spinner carCategoryEdit = dialog.findViewById(R.id.carCategoryEdit);
        final EditText carPriceHourEdit = dialog.findViewById(R.id.carPriceHourEdit);
        final EditText carPriceDayEdit = dialog.findViewById(R.id.carPriceDayEdit);
        final EditText carMakerEdit = dialog.findViewById(R.id.carMakerEdit);
        final EditText carModelEdit = dialog.findViewById(R.id.carModelEdit);
        final EditText carColorEdit = dialog.findViewById(R.id.carColorEdit);
        final CheckBox carAvailable = dialog.findViewById(R.id.carAvailableCheckBox);
        final EditText carSeats = dialog.findViewById(R.id.carSeats);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.car_category_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        carCategoryEdit.setAdapter(adapter);

        //set all fields
        carAvailable.setChecked(car.getAvailability());
        carPriceHourEdit.setText(String.valueOf(car.getPricePerHour()));
        carPriceDayEdit.setText(String.valueOf(car.getPricePerDay()));
        carMakerEdit.setText(car.getCarMake());
        carModelEdit.setText(car.getCarModel());
        carColorEdit.setText(car.getColor());
        carSeats.setText(String.valueOf(car.getSeats()));
        int position=0;
        switch (car.getCategory()){
            case "SUV":
                position =1;
                break;
            case "Hatchback":
                position =2;
                break;
            case "Truck":
                position =3;
                break;
            case "Van":
                position =4;
                break;
            case "Sedan":
                position =5;
                break;
            case "Sports Car":
                position =6;
                break;

        }
        carCategoryEdit.setSelection(position);

        final Button addnew = dialog.findViewById(R.id.btnAddNew);
        Button update = dialog.findViewById(R.id.btnUpdate);
        addnew.setVisibility(View.GONE);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carCategoryEdit.getSelectedItemPosition()==0){
                    Toast.makeText(context, "Please select a category.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carMakerEdit.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter a car maker.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carModelEdit.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please enter a car model.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carColorEdit.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter a car color.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carPriceDayEdit.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter a daily price.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carPriceHourEdit.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter a hourly price.", Toast.LENGTH_SHORT).show();
                    return;
                }if(carSeats.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Please enter car seats.", Toast.LENGTH_SHORT).show();
                    return;
                }
                car.setCarMake(carMakerEdit.getText().toString());
                car.setCarModel(carModelEdit.getText().toString());
                car.setCategory(carCategoryEdit.getSelectedItem().toString());
                car.setColor(carColorEdit.getText().toString());
                car.setPricePerDay(Double.parseDouble(carPriceDayEdit.getText().toString()));
                car.setPricePerHour(Double.parseDouble(carPriceHourEdit.getText().toString()));
                car.setSeats(Integer.parseInt(carSeats.getText().toString()));
                car.setAvailibality(carAvailable.isChecked());
                // call the update function from CarCollection
                CarCollection.createAndUpdateCar(context, db, car, false);
                //hide the dialog
                dialog.dismiss();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

package com.example.week6labcarrental.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.model.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.google.common.reflect.Reflection.initialize;

public class SaleActivity2 extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    Button pickupDate, returnDate, btnCalc, pickupTime, returnTime;
    double totalPrice;
    String pTime, rTime;
    private int pHour, rHour, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale2);
        btnCalc = findViewById(R.id.calculatePrice);
        Button btnBook = findViewById(R.id.book);
        TextView txtCar = findViewById(R.id.txtCar);
        final TextView total = findViewById(R.id.txtTotalAmount);
        final EditText txtName = findViewById(R.id.txtName);
        final EditText txtDriverLicense = findViewById(R.id.txtDriverLicense);
        final EditText txtDeposite = findViewById(R.id.txtDeposit);
        Intent intent = getIntent();

        final Car selectedCar = (Car) intent.getSerializableExtra("Cardata");
        final String datePickup;
        String dateReturn;
        final String MY_PREFS_NAME = "MyPrefsFile";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String id = UUID.randomUUID().toString();
        mAuth = FirebaseAuth.getInstance();
        initialize();

        StringBuilder s = new StringBuilder();
        s.append("Car : " + selectedCar.getCarMake() + " " + selectedCar.getCarModel() + "\n");
        s.append("Price per hour: " + selectedCar.getPricePerHour() + "\n");
        s.append("Price per day: " + selectedCar.getPricePerDay());
        txtCar.setText(s);

        final StringBuilder s1 = new StringBuilder();
        //s1.append(make + " " + model);
        final String car1 = s1.toString();
        datePickup = prefs.getString("datePickup","");

        StringBuilder sb = new StringBuilder();
        sb.append("Pickup Date : " + datePickup + "\n");
        // set total is 0 at first
        total.setText("Total: $0");
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        pickupDate = findViewById(R.id.pickupDate);
        returnDate = findViewById(R.id.returnDate);
        pickupTime = findViewById(R.id.pickupTime);
        returnTime = findViewById(R.id.returnTime);


        final LinearLayout timePick = findViewById(R.id.timeChose);
        final CheckBox carHourlyRent = findViewById(R.id.carHourlyRent);
        carHourlyRent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    timePick.setVisibility(View.VISIBLE);
                    returnDate.setVisibility(View.GONE);
                } else {
                    timePick.setVisibility(View.GONE);
                    returnDate.setVisibility(View.VISIBLE);
                }
            }
        });
        pickupDate.setOnClickListener(new View.OnClickListener() {
            Calendar cal = Calendar.getInstance();
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SaleActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        String date = day + "/" + month + "/" + year;

                        pickupDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        returnDate.setOnClickListener(new View.OnClickListener() {
            Calendar cal = Calendar.getInstance();
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SaleActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        String date = day + "/" + month + "/" + year;

                        returnDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(SaleActivity2.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                pickupTime.setText(hourOfDay + ":" + minute);
                                pHour = hourOfDay;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
        returnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(SaleActivity2.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                returnTime.setText(hourOfDay + ":" + minute);
                                rHour = hourOfDay;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carHourlyRent.isChecked()) {
                    int hour = rHour - pHour;
                    totalPrice = hour * selectedCar.getPricePerHour() + Double.parseDouble(txtDeposite.getText().toString());
                    total.setText("Total: $" + totalPrice);
                } else {
                    Date startDateValue = new Date(pickupDate.getText().toString());
                    Date endDateValue = new Date(returnDate.getText().toString());
                    long diff = endDateValue.getTime() - startDateValue.getTime();
                    int day = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    totalPrice = day * selectedCar.getPricePerDay() + Double.parseDouble(txtDeposite.getText().toString());
                    total.setText("Total: $" + totalPrice);
                }
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String license = txtDriverLicense.getText().toString();

                Double deposit = Double.parseDouble(txtDeposite.getText().toString());
                //Booking booking = new Booking(id,name,license,deposit,car,pHour,pDay,datePickup);
                //db.collection("bookings").document(b.getID()).set(b);

                Map<String,Object> m = new HashMap<>();
                m.put("name",name);
                m.put("license",license);
                m.put("deposit",deposit);
                m.put("car",selectedCar);
                m.put("total",totalPrice);
                if(carHourlyRent.isChecked()) {
                    m.put("pickupTime",pickupTime.getText().toString());
                    m.put("returnTime",pickupTime.getText().toString());
                    m.put("returnDate",pickupDate.getText().toString());
                } else {
                    m.put("pickupTime","12:00");
                    m.put("returnTime","12:00");

                }
                m.put("pickupDate",pickupDate.getText().toString());

                db.collection("bookings")
                        .document(id)
                        .set(m)
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

                Toast.makeText(getApplicationContext(),"Booking succesfully added",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SaleActivity2.this,SaleActivity.class);
                SaleActivity2.this.startActivity(i);
            }
        });
    }
}

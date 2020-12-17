package com.example.week6labcarrental.ui;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.adapter.CarRecyclerAdapter;
import com.example.week6labcarrental.adapter.ItemClickListener;
import com.example.week6labcarrental.controller.Authentication;
import com.example.week6labcarrental.firebase.CarCollection;
import com.example.week6labcarrental.model.Car;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Calendar;

public class ClientActivity extends AppCompatActivity implements ItemClickListener {

    // buttons
    Button btnPickup, btnContinue;
    // date
    DatePickerDialog.OnDateSetListener setListener;

    TextView txtpickup, txtreturn;
    String pickupdate, returndate; // serves as holder for the dates
    // Firebase auth
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public static final String COLLECTION_NAME = "cars";
    private final String[] key = {"carId", "category", "pricePerHour", "pricePerDay", "carMake", "carModel", "color", "availability"};

    ArrayList<Car> carsList;

    BroadcastReceiver response;
    RecyclerView recyclerView;
    CarRecyclerAdapter carRecyclerAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        carsList = new ArrayList<>();
        initialize();
        recyclerView =  findViewById(R.id.myRec);
        carRecyclerAdapter2 = new CarRecyclerAdapter(carsList, this);
        recyclerView.setAdapter(carRecyclerAdapter2);
        recyclerView.setLayoutManager(new GridLayoutManager(ClientActivity.this, 1));

        btnContinue = findViewById(R.id.btnContinue);
        btnPickup = findViewById(R.id.btnPickup);
        txtpickup = findViewById(R.id.txtPickup);

        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);

        btnPickup.setOnClickListener(new View.OnClickListener() {
            Calendar cal = Calendar.getInstance();
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ClientActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month += 1;
                        String date = day + "/" + month + "/" + year;
                        pickupdate = date;
                        txtpickup.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = day + "/" + month + "/" + year;
            }
        };


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        response = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case CarCollection.LOAD_CAR_DATA_DONE:
                        //get car Data from intent
                        carsList = (ArrayList<Car>) intent.getSerializableExtra("cars_data");
                        Log.i("carlist", String.valueOf(carsList.size()));
                        carRecyclerAdapter2.changeData(carsList);
                        carRecyclerAdapter2.notifyDataSetChanged();
                        break;
                }
            }
        };

    }
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Authentication.checkSignIn(mAuth, this);
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
        IntentFilter filter = new IntentFilter();
        filter.addAction(CarCollection.LOAD_CAR_DATA_DONE);
        registerReceiver(response,filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_signout:
                //Do sign out
                mAuth.signOut();
                Authentication.checkSignIn(mAuth, this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        db.setFirestoreSettings(settings);
        //get all the cars
        CarCollection.getAllCars(this,db);
    }

    @Override
    public void onItemClick(View view, int position) {
        Car clickedCar = carsList.get(position);
        Intent i = new Intent(this, SaleActivity2.class);
        i.putExtra("car",clickedCar);
        startActivity(i);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


}

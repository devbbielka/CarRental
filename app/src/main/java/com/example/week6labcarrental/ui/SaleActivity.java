package com.example.week6labcarrental.ui;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.adapter.ItemClickListener;
import com.example.week6labcarrental.adapter.MyRecyclerAdapter;
import com.example.week6labcarrental.controller.Authentication;
import com.example.week6labcarrental.controller.ProcessData;
import com.example.week6labcarrental.firebase.CarCollection;
import com.example.week6labcarrental.fragment.CarListFragment;
import com.example.week6labcarrental.fragment.CustomerNeedsFragment;
import com.example.week6labcarrental.model.Car;
import com.example.week6labcarrental.model.CustomerNeeds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;
import java.util.Calendar;

public class SaleActivity extends AppCompatActivity implements ItemClickListener, CustomerNeedsFragment.OnFragmentInteractionListener
                ,CarListFragment.OnFragmentInteractionListener
{

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    public static final String COLLECTION_NAME = "cars";
    ArrayList<Car> carsList, carNeedList;
    private final String[] key = {"carId", "category", "pricePerHour", "pricePerDay", "carMake", "carModel", "color", "availability"};
    BroadcastReceiver response;
    String pickupdate,returndate;
    RecyclerView recyclerView;
    MyRecyclerAdapter carRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        carsList = new ArrayList<>();
        initialize();

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        CustomerNeedsFragment customerNeedsFragment = new CustomerNeedsFragment();

        ft.add(R.id.fragmentContainer, customerNeedsFragment.newInstance());
        ft.commit();
//        recyclerView =  findViewById(R.id.myRec);
//        carRecyclerAdapter = new MyRecyclerAdapter(carsList, this);
//        recyclerView.setAdapter(carRecyclerAdapter);
//        recyclerView.setLayoutManager(new GridLayoutManager(SaleActivity.this, 1));
//
        response = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case CarCollection.LOAD_CAR_DATA_DONE:
                        //get car Data from intent
                        carsList = (ArrayList<Car>) intent.getSerializableExtra("cars_data");
                        Log.i("carlist", String.valueOf(carsList.size()));
//                        carRecyclerAdapter.changeData(carsList);
//                        carRecyclerAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void initialize() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().build();
        db.setFirestoreSettings(settings);
        //get all the cars
        CarCollection.getAllCars(this,db);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Authentication.checkSignIn(mAuth, this);
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
                FirebaseAuth.getInstance().signOut();
                Authentication.checkSignIn(mAuth, this);
                break;

        }
        return super.onOptionsItemSelected(item);
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
    public void onItemClick(View view, int position) {
        Car clickedCar = carsList.get(position);
        Intent i = new Intent(this, SaleActivity2.class);
        i.putExtra("make",clickedCar.getCarMake());
        i.putExtra("model",clickedCar.getCarModel());
        i.putExtra("pHour",clickedCar.getPricePerHour());
        i.putExtra("pDay",clickedCar.getPricePerDay());
        startActivity(i);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onProvideCustomerNeeds(CustomerNeeds needs) {
        carNeedList = ProcessData.processCustomerNeed(needs, carsList);

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        CarListFragment carListFragment = new CarListFragment();

        ft.replace(R.id.fragmentContainer, carListFragment.newInstance(carNeedList));
        ft.commit();
        if(carNeedList == null) {
            Toast.makeText(this, "No Car found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCarItemListener(Car car) {
        Intent intent = new Intent(SaleActivity.this, SaleActivity2.class);
        intent.putExtra("Cardata", car);
        startActivity(intent);
    }
}

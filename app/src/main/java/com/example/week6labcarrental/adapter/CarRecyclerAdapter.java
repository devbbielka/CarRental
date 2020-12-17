package com.example.week6labcarrental.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.model.Car;

import java.util.ArrayList;

public class CarRecyclerAdapter extends RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder> {
    public static final String TAG = "CarRecyclerAdapter";
    private ItemClickListener mClickListener;
    private ArrayList<Car> mData;

    public CarRecyclerAdapter(){

    }

    public CarRecyclerAdapter( ArrayList<Car> data, ItemClickListener mClickListener){
        this.mData = data;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater linf = LayoutInflater.from(viewGroup.getContext());
        View itemView = linf.inflate(R.layout.row_available_car, viewGroup, false);
        ViewHolder itemViewHolder = new ViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //viewHolder.category.setText(mData.get(position).getCategory());
        viewHolder.availability.setText(String.valueOf(mData.get(position).getAvailability()));
        //viewHolder.carPriceDay.setText(String.valueOf(mData.get(position).getPricePerDay()));
       // viewHolder.carPriceHour.setText(String.valueOf(mData.get(position).getPricePerHour()));
        viewHolder.seats.setText(String.valueOf(mData.get(position).getSeats()));
        viewHolder.carModel.setText(mData.get(position).getCarModel());
        viewHolder.carMake.setText(mData.get(position).getCarMake());
        viewHolder.color.setText(mData.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        if(mData != null) {
            return mData.size();
        } return 0;
    }

    public void changeData( ArrayList<Car> data){
        this.mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView category, carMake, carModel, carPriceHour, carPriceDay, seats, availability, color;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // category = itemView.findViewById(R.id.txtCategory);
            carMake = itemView.findViewById(R.id.txtCarMakeInfo);
            carModel = itemView.findViewById(R.id.txtCarModelInfo);
          //  carPriceHour = itemView.findViewById(R.id.txtPriceHour);
          //  carPriceDay = itemView.findViewById(R.id.txtPriceDay);
            seats = itemView.findViewById(R.id.txtSeatsInfo);
            availability = itemView.findViewById(R.id.txtAvailableInfo);
            color = itemView.findViewById(R.id.txtColorInfo);
        }
    }
}

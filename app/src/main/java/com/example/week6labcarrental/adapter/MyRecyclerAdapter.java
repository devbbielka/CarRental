package com.example.week6labcarrental.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.week6labcarrental.R;
import com.example.week6labcarrental.model.Car;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    public static final String TAG = "MyRecyclerAdapter";
    private ItemClickListener mClickListener;
    private ArrayList<Car> mData;

    public MyRecyclerAdapter() {
    }

    public MyRecyclerAdapter( ArrayList<Car> data, ItemClickListener mClickListener){
        this.mData = data;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater linf = LayoutInflater.from(viewGroup.getContext());
        View itemView = linf.inflate(R.layout.layout_item, viewGroup, false);
        ViewHolder itemViewHolder = new ViewHolder(itemView);
        return itemViewHolder;



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.category.setText(mData.get(position).getCategory());
        viewHolder.availability.setText(String.valueOf(mData.get(position).getAvailability()));
        viewHolder.carPriceDay.setText(String.valueOf(mData.get(position).getPricePerDay()));
        viewHolder.carPriceHour.setText(String.valueOf(mData.get(position).getPricePerHour()));
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView category, carMake, carModel, carPriceHour, carPriceDay, seats, availability, color;
        ImageButton btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.txtCategory);
            carMake = itemView.findViewById(R.id.txtCarMake);
            carModel = itemView.findViewById(R.id.txtCarModel);
            carPriceHour = itemView.findViewById(R.id.txtPriceHour);
            carPriceDay = itemView.findViewById(R.id.txtPriceDay);
            seats = itemView.findViewById(R.id.txtSeats);
            availability = itemView.findViewById(R.id.txtAvailability);
            color = itemView.findViewById(R.id.txtColor);
            btn = itemView.findViewById(R.id.deleteBtn);

            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
            btn.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            Log.d("TAG","View: Clicked!");
        }

        @Override
        public boolean onLongClick(View v) {
            mClickListener.onItemLongClick(v, getAdapterPosition());
            Log.d("TAG","View:Long Clicked!");
            if(v.getId() == R.id.txtColor) {
                mClickListener.onItemLongClick(v,getAdapterPosition() );
            }

            return true;
        }

    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
        Log.d("TAG","Success: Clicked!");
    }

}

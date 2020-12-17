package com.example.week6labcarrental.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.model.Car;
import com.example.week6labcarrental.model.User;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder>{
    private ItemClickListener mClickListener;
    private List<User> mData;

    public UserRecyclerAdapter() {
    }

    public UserRecyclerAdapter(List<User> data, ItemClickListener mClickListener){
        this.mData = data;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public UserRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater linf = LayoutInflater.from(viewGroup.getContext());
        View itemView = linf.inflate(R.layout.layout_user_item, viewGroup, false);
        UserRecyclerAdapter.ViewHolder itemViewHolder = new UserRecyclerAdapter.ViewHolder(itemView);
        return itemViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerAdapter.ViewHolder viewHolder, int position) {
        viewHolder.userId.setText(mData.get(position).getUserId());
        viewHolder.fullName.setText(String.valueOf(mData.get(position).getFullName()));
        viewHolder.email.setText(mData.get(position).getEmail());
        String userRole = "";
        switch (mData.get(position).getRole()) {
            case 1: userRole = "Client";
            break;
            case 2: userRole = "Sale";
                break;
            case 3: userRole = "Manager";
                break;
        }
        viewHolder.role.setText(userRole);


    }

    @Override
    public int getItemCount() {
        if(mData != null) {
            return mData.size();
        } return 0;
    }

    /**
     * call to change the data inside adapter
     * @param data user array list
     */
    public void changeData( List<User> data){
        this.mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView userId, fullName, email, role;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.itemUserId);
            email = itemView.findViewById(R.id.itemEmail);
            fullName = itemView.findViewById(R.id.itemFullName);
            role = itemView.findViewById(R.id.itemRole);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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

}

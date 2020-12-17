package com.example.week6labcarrental.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.adapter.ItemClickListener;
import com.example.week6labcarrental.adapter.MyRecyclerAdapter;
import com.example.week6labcarrental.adapter.UserRecyclerAdapter;
import com.example.week6labcarrental.model.Car;
import com.example.week6labcarrental.ui.ManagerActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CarListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarListFragment extends Fragment {
    private static final String ARG_PARAM1 = "carlist";

    private ArrayList<Car> carArrayList;

    private OnFragmentInteractionListener mListener;

    public CarListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param carArrayList Parameter 1.
     * @return A new instance of fragment CarListFragment.
     */
    public static CarListFragment newInstance(ArrayList<Car> carArrayList) {
        CarListFragment fragment = new CarListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, carArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carArrayList = (ArrayList<Car>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        MyRecyclerAdapter carRecyclerAdapter;
        recyclerView =  view.findViewById(R.id.myRec);
        carRecyclerAdapter = new MyRecyclerAdapter(carArrayList, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mListener.onCarItemListener(carArrayList.get(position));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setAdapter(carRecyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onCarItemListener(Car car);
    }
}

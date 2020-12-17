package com.example.week6labcarrental.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.week6labcarrental.R;
import com.example.week6labcarrental.model.CustomerNeeds;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CustomerNeedsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CustomerNeedsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerNeedsFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public CustomerNeedsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomerNeedsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerNeedsFragment newInstance() {
        CustomerNeedsFragment fragment = new CustomerNeedsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_needs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Spinner carCategoryEdit = view.findViewById(R.id.carCategoryEdit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.car_category_list, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        carCategoryEdit.setAdapter(adapter);
        final EditText color = view.findViewById(R.id.carColor);
        final EditText carSeats = view.findViewById(R.id.carSeats);
        final EditText carHighestPrice = view.findViewById(R.id.carHighestPrice);
        final EditText carLowestPrice = view.findViewById(R.id.carLowestPrice);
        final CheckBox carHourlyRent = view.findViewById(R.id.carHourlyRent);


        Button search = view.findViewById(R.id.btnSearchNeeds);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomerNeeds customerNeeds = new CustomerNeeds();
                if(!color.getText().toString().isEmpty())
                    customerNeeds.setColor(color.getText().toString());
                if(!carSeats.getText().toString().isEmpty())
                    customerNeeds.setNumOfSeats(Integer.parseInt(carSeats.getText().toString()));
                else customerNeeds.setNumOfSeats(0);
                if(!carLowestPrice.getText().toString().isEmpty())
                    customerNeeds.setPriceFrom(Double.parseDouble(carLowestPrice.getText().toString()));
                else customerNeeds.setPriceFrom(0);
                if(!carHighestPrice.getText().toString().isEmpty())
                    customerNeeds.setPriceTo(Double.parseDouble(carHighestPrice.getText().toString()));
                else customerNeeds.setPriceTo(0);
                if(carCategoryEdit.getSelectedItemPosition() != 0) {
                    customerNeeds.setVehicleType(carCategoryEdit.getSelectedItem().toString());
                }
                customerNeeds.setHourlyRent(carHourlyRent.isChecked());
                mListener.onProvideCustomerNeeds(customerNeeds);
            }
        });
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
        void onProvideCustomerNeeds(CustomerNeeds needs);
    }
}

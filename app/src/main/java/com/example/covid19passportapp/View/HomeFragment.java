package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.covid19passportapp.R;

import org.w3c.dom.Text;


public class HomeFragment extends Fragment {

    private TextView passportID;
    private TextView fullName;
    private TextView birthdate;
    private TextView country;
    private TextView vaccinationDate;
    private TextView vaccineType;
    private TextView immuneUntil;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        passportID = view.findViewById(R.id.passportIDValue);
        fullName = view.findViewById(R.id.fullNameValue);
        birthdate = view.findViewById(R.id.birthdateValue);
        country = view.findViewById(R.id.countryValue);
        vaccinationDate  = view.findViewById(R.id.vaccineDateValue);
        vaccineType = view.findViewById(R.id.vaccineTypeValue);
        immuneUntil = view.findViewById(R.id.immuneUntilValue);


        return view;
    }
}
package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.CitizenViewModel;
import com.example.covid19passportapp.ViewModel.PassportViewModel;
import com.example.covid19passportapp.ViewModel.TestsViewModel;

import org.w3c.dom.Text;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.View.inflate;


public class HomeFragment extends Fragment {

    private PassportViewModel passportViewModel;
    private CitizenViewModel citizenViewModel;

    private ConstraintLayout passportNotCreated;
    private ConstraintLayout passportCreated;

    private Button createPassportForward;

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
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        passportViewModel = new ViewModelProvider(this).get(PassportViewModel.class);
        citizenViewModel = new ViewModelProvider(this).get(CitizenViewModel.class);

        //Passport Observer
        Observer<Passport> passportObserver = passport -> {
            passportID.setText(passport.getId());
            country.setText(passport.getCountry());
            vaccinationDate.setText(passport.getVaccinationDate().toString("dd/MM/yyyy"));
            vaccineType.setText(passport.getVaccineType());
            immuneUntil.setText(passport.getImmuneUntil().toString("dd/MM/yyyy"));
        };

        passportNotCreated = view.findViewById(R.id.passportNotCreatedConstraintLayout);
        passportCreated = view.findViewById(R.id.passportCreatedConstraintLayout);

        createPassportForward = view.findViewById(R.id.createPassportButtonForward);
        createPassportForward.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.openCreatePassportFragmentAction));

        passportID = view.findViewById(R.id.passportIDValue);
        fullName = view.findViewById(R.id.fullNameValue);
        birthdate = view.findViewById(R.id.birthdateValue);
        country = view.findViewById(R.id.countryValue);
        vaccinationDate  = view.findViewById(R.id.vaccineDateValue);
        vaccineType = view.findViewById(R.id.vaccineTypeValue);
        immuneUntil = view.findViewById(R.id.immuneUntilValue);

        citizenViewModel.getFullName().observe(getViewLifecycleOwner(), (fullName) -> this.fullName.setText(fullName));
        citizenViewModel.getBirthdate().observe(getViewLifecycleOwner(), (birthdate) -> this.birthdate.setText(birthdate.toString("dd/MM/yyyy")));
        passportViewModel.getPassport().observe(getViewLifecycleOwner(), passportObserver);

        if (passportViewModel.isPassportCreated()) {
            passportCreated.setVisibility(VISIBLE);
            passportNotCreated.setVisibility(GONE);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.language_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
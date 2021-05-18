package com.example.covid19passportapp.View;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.CitizenViewModel;
import com.example.covid19passportapp.ViewModel.PassportViewModel;
import com.example.covid19passportapp.ViewModel.TestsViewModel;

import org.w3c.dom.Text;

import java.util.Locale;

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
    private ImageView qrCode;
    private Locale myLocale;


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
        qrCode = view.findViewById(R.id.qrCodeImageView);

        citizenViewModel.getFullName().observe(getViewLifecycleOwner(), (fullName) -> this.fullName.setText(fullName));
        citizenViewModel.getBirthdate().observe(getViewLifecycleOwner(), (birthdate) -> this.birthdate.setText(birthdate.toString("dd/MM/yyyy")));

        passportViewModel.isPassportCreated().observe(getViewLifecycleOwner(), (c) -> {
            if (c) {
                passportCreated.setVisibility(VISIBLE);
                passportNotCreated.setVisibility(GONE);

                //Passport Observer
                Observer<Passport> passportObserver = passport -> {
                    passportID.setText(passport.getId());
                    country.setText(passport.getCountry());
                    vaccinationDate.setText(passport.getVaccinationDate().toString("dd/MM/yyyy"));
                    vaccineType.setText(passport.getVaccineType());
                    immuneUntil.setText(passport.getImmuneUntil().toString("dd/MM/yyyy"));
                    Glide.with(this).load(passportViewModel.getQrCodeUrl(passport)).into(qrCode);
                    Log.d("GLIDE", passportViewModel.getQrCodeUrl(passport));
                };
                passportViewModel.getPassport().observe(getViewLifecycleOwner(), passportObserver);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.language_menu, menu);

        menu.getItem(0).setOnMenuItemClickListener(item -> {
            setLocale("en");
            return true;
        });
        menu.getItem(1).setOnMenuItemClickListener(item -> {
            setLocale("ro-rMD");
            return true;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setLocale(String lang) {
        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Activity activity = getActivity();
        getActivity().finish();
        activity.startActivity(activity.getIntent());
    }

}
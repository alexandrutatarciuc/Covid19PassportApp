package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.CitizenViewModel;
import com.example.covid19passportapp.ViewModel.PassportViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class CreatePassportFragment extends Fragment {

    private PassportViewModel passportViewModel;
    private CitizenViewModel citizenViewModel;

    private MaterialDatePicker birthdateDatePicker;
    private MaterialDatePicker vaccinationDateDatePicker;
    private MaterialDatePicker immuneUntilDatePicker;

    private TextInputEditText IDEditInput;
    private TextInputEditText fullNameEditInput;
    private TextInputEditText birthdateEditInput;
    private TextInputEditText countryEditInput;
    private TextInputEditText vaccineTypeEditInput;
    private TextInputEditText vaccineDateEditInput;
    private TextInputEditText immuneUntilEditInput;

    private Button createPassportButton;


    public CreatePassportFragment() {
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
        View view = inflater.inflate(R.layout.fragment_create_passport, container, false);

        passportViewModel = new ViewModelProvider(this).get(PassportViewModel.class);
        citizenViewModel = new ViewModelProvider(this).get(CitizenViewModel.class);

        IDEditInput = view.findViewById(R.id.createPassportIDEditInput);
        fullNameEditInput = view.findViewById(R.id.createPassportFullNameEditInput);
        birthdateEditInput = view.findViewById(R.id.createPassportBirthdateEditInput);
        countryEditInput = view.findViewById(R.id.createPassportCountryEditInput);
        vaccineTypeEditInput = view.findViewById(R.id.createPassportVaccineTypeEditInput);
        vaccineDateEditInput = view.findViewById(R.id.createPassportVaccineDateEditInput);
        immuneUntilEditInput = view.findViewById(R.id.createPassportImmuneUntilEditInput);
        createPassportButton = view.findViewById(R.id.createPassportButton);

        vaccinationDateDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Vaccination Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        immuneUntilDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Immune Until")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        vaccineDateEditInput.setOnClickListener(v -> vaccinationDateDatePicker.show(this.getParentFragmentManager(), "VACCINATION_DATE_PICKER"));
        immuneUntilEditInput.setOnClickListener(v -> immuneUntilDatePicker.show(this.getParentFragmentManager(), "IMMUNE_UNTIL_DATE_PICKER"));


        vaccinationDateDatePicker.addOnPositiveButtonClickListener((selection) -> {
            DateTime selectedDate = new DateTime(selection);
            vaccineDateEditInput.setText(selectedDate.toString("dd/MM/yyyy"));
        });

        immuneUntilDatePicker.addOnPositiveButtonClickListener((selection) -> {
            DateTime selectedDate = new DateTime(selection);
            immuneUntilEditInput.setText(selectedDate.toString("dd/MM/yyyy"));
        });

        citizenViewModel.getFullName().observe(getViewLifecycleOwner(), (fullName) -> fullNameEditInput.setText(fullName));
        citizenViewModel.getBirthdate().observe(getViewLifecycleOwner(), (birthdate) -> birthdateEditInput.setText(birthdate.toString("dd/MM/yyyy")));

        //Validating input
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isCompletedID = !IDEditInput.getText().toString().isEmpty();
                boolean isCompletedCountry = !countryEditInput.getText().toString().isEmpty();
                boolean isCompletedVaccineType = !vaccineTypeEditInput.getText().toString().isEmpty();
                boolean isCompletedVaccineDate = !vaccineDateEditInput.getText().toString().isEmpty();
                boolean isCompletedImmuneUntil = !immuneUntilEditInput.getText().toString().isEmpty();
                boolean setEnabled = isCompletedID && isCompletedCountry && isCompletedVaccineType && isCompletedVaccineDate && isCompletedImmuneUntil;

                createPassportButton.setEnabled(setEnabled);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //nothing
            }
        };

        //Adding textWatcher
        IDEditInput.addTextChangedListener(textWatcher);
        fullNameEditInput.addTextChangedListener(textWatcher);
        birthdateEditInput.addTextChangedListener(textWatcher);
        countryEditInput.addTextChangedListener(textWatcher);
        vaccineTypeEditInput.addTextChangedListener(textWatcher);
        vaccineDateEditInput.addTextChangedListener(textWatcher);
        immuneUntilEditInput.addTextChangedListener(textWatcher);

        //ButtonOnClick
        createPassportButton.setOnClickListener(v -> {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime vaccineDate = dtf.parseDateTime(vaccineDateEditInput.getText().toString());
            DateTime immuneUntil = dtf.parseDateTime(immuneUntilEditInput.getText().toString());

            Passport createdPassport = new Passport(IDEditInput.getText().toString(), countryEditInput.getText().toString(),
                    vaccineDate, vaccineTypeEditInput.getText().toString(), immuneUntil);
            passportViewModel.setPassport(createdPassport);

            /*Further research on this way of solving BackStack behaviour might be needed when more fragments will be added*/
            NavHostFragment.findNavController(this).popBackStack(); //Pops CreatePassportFragment
            NavHostFragment.findNavController(this).popBackStack(); //Pops Out-Dated HomeFragment
            NavHostFragment.findNavController(this).navigate(R.id.homeFragment);
        });

        return view;
    }
}
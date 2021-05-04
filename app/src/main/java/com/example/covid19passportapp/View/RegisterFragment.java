package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.covid19passportapp.Models.Citizen;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.CitizenViewModel;
import com.example.covid19passportapp.ViewModel.PassportViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class RegisterFragment extends Fragment {

    private CitizenViewModel citizenViewModel;

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText fullNameInput;
    private TextInputEditText birthdateInput;
    private MaterialDatePicker birthdateDatePicker;

    private Button registerButton;
    private Button backToLoginButton;

    public RegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        citizenViewModel =  new ViewModelProvider(this).get(CitizenViewModel.class);

        emailInput = view.findViewById(R.id.registerEmailEditInput);
        passwordInput = view.findViewById(R.id.registerPasswordEditInput);
        fullNameInput = view.findViewById(R.id.registerFullNameEditInput);
        birthdateInput = view.findViewById(R.id.registerBirthdateEditInput);
        registerButton = view.findViewById(R.id.registerButton);
        backToLoginButton = view.findViewById(R.id.goBackToLoginButton);

        birthdateDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Birthdate")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        registerButton.setOnClickListener(v -> register());
        backToLoginButton.setOnClickListener(v -> backToLogin());
        birthdateInput.setOnClickListener(v -> birthdateDatePicker.show(this.getParentFragmentManager(), "BIRTHDATE_PICKER"));

        birthdateDatePicker.addOnPositiveButtonClickListener((selection) -> {
            DateTime selectedDate = new DateTime(selection);
            birthdateInput.setText(selectedDate.toString("dd/MM/yyyy"));
        });

        //Validating input
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean isFilledEmail = !emailInput.getText().toString().isEmpty();
                boolean isFilledPassword = !passwordInput.getText().toString().isEmpty();
                boolean isFilledFullName = !fullNameInput.getText().toString().isEmpty();
                boolean isFilledBirthdate = !passwordInput.getText().toString().isEmpty();
                boolean setEnabled = isFilledEmail && isFilledPassword && isFilledFullName && isFilledBirthdate;
                registerButton.setEnabled(setEnabled);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //nothing
            }
        };

        emailInput.addTextChangedListener(textWatcher);
        passwordInput.addTextChangedListener(textWatcher);
        fullNameInput.addTextChangedListener(textWatcher);
        birthdateInput.addTextChangedListener(textWatcher);

        return view;
    }

    private void register() {
        Log.d("REGISTER", "CLICKED");

        //TODO move to ViewModel
        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //Toast.makeText(getContext(), "User successfully registered", Toast.LENGTH_SHORT).show();
                    DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                    DateTime birthdate = dtf.parseDateTime(birthdateInput.getText().toString());
                    citizenViewModel.addCitizen(new Citizen(fullNameInput.getText().toString(), birthdate));
                    Log.d("REGISTER_FRAGMENT","I GOT HERE");
                    backToLogin();
                } else {
                    Log.d("REGISTER", task.getException().getMessage());
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backToLogin() {
        NavHostFragment.findNavController(this).popBackStack();
       /* NavHostFragment.findNavController(this).popBackStack();
        NavHostFragment.findNavController(this).navigate(R.id.loginFragment);*/
    }
}
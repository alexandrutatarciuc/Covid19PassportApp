package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.covid19passportapp.Models.Test;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.TestsViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;

public class AddTestFragment extends Fragment {

    private TestsViewModel testsViewModel;

    private MaterialDatePicker datePicker;
    private TextInputEditText dateInput;
    private DateTime selectedDate;
    private AutoCompleteTextView resultACT;
    private Button addTestButton;

    private ArrayList<String> resultTypes;
    private ArrayAdapter<String> resultTypesAdapter;


    public AddTestFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_test, container, false);

        //TestsViewModel
        testsViewModel = new ViewModelProvider(this).get(TestsViewModel.class);

        dateInput = view.findViewById(R.id.testDateEditInput);
        resultACT = view.findViewById(R.id.testResultInputAutoComplete);
        addTestButton = view.findViewById(R.id.addTestButton);

        datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Testing Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

        //Redundant
        datePicker.addOnCancelListener((dialogInterface) -> {
            dateInput.clearFocus();
        });

        //Redundant
        datePicker.addOnDismissListener((dialogInterface) -> {
            dateInput.clearFocus();
        });

        datePicker.addOnPositiveButtonClickListener((selection) -> {
            selectedDate = new DateTime(selection);
            dateInput.setText(selectedDate.toString("dd/MM/yyyy"));
        });

        //Redundant
        dateInput.setOnFocusChangeListener((v1, s) -> {
            if (v1.getId() == R.id.testDateEditInput && s) {
                datePicker.show(this.getParentFragmentManager(), "DATE_PICKER");
            }
        });

        dateInput.setOnClickListener((v) -> {
            datePicker.show(this.getParentFragmentManager(), "DATE_PICKER");
        });

        resultTypes = new ArrayList<>();
        resultTypes.add("NEGATIVE");
        resultTypes.add("POSITIVE");
        resultTypes.add("UNKNOWN");

        resultTypesAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, resultTypes);
        resultACT.setAdapter(resultTypesAdapter);

        addTestButton.setOnClickListener(v -> {
            Log.d("BUTTON_PRESSED", dateInput.getText().toString().concat(resultACT.getText().toString()));

            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime dateTime = dtf.parseDateTime(dateInput.getText().toString());
            testsViewModel.addTest(new Test(dateTime, resultACT.getText().toString()));
            NavHostFragment.findNavController(this).navigate(R.id.openTestsFragmentAfterAddAction);
        });


        return view;
    }


}
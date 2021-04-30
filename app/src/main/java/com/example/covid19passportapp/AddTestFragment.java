package com.example.covid19passportapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.joda.time.DateTime;

import java.util.Date;

public class AddTestFragment extends Fragment {

    private MaterialDatePicker datePicker;
    private TextInputEditText dateInput;
    private DateTime selectedDate;

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

        datePicker =
                MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Testing Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();

        datePicker.addOnCancelListener((dialogInterface) -> {
            dateInput.clearFocus();
        });

        datePicker.addOnDismissListener((dialogInterface) -> {
            dateInput.clearFocus();
        });

        datePicker.addOnPositiveButtonClickListener((selection) -> {
            selectedDate = new DateTime(selection);
            dateInput.setText(selectedDate.toString("dd/MM/yyyy"));
        });

        dateInput = view.findViewById(R.id.testDateEditInput);

        dateInput.setOnFocusChangeListener((v1, s) -> {
            if (v1.getId() == R.id.testDateEditInput && s) {
                datePicker.show(this.getParentFragmentManager(), "DATE_PICKER");
            }
        });

        dateInput.setOnClickListener((v) -> {
            datePicker.show(this.getParentFragmentManager(), "DATE_PICKER");
        });


        return view;


    }


}
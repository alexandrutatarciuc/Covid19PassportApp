package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid19passportapp.Models.Test;
import com.example.covid19passportapp.Models.TestsRecyclerAdapter;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.TestsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class TestsFragment extends Fragment {

    private TestsViewModel testsViewModel;
    private FloatingActionButton actionButton;
    private RecyclerView recyclerView;

    public TestsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_tests, container, false);

        //Floating action button
        actionButton = view.findViewById(R.id.testsFab);
        actionButton.setOnClickListener((v) -> NavHostFragment.findNavController(this).navigate(R.id.openAddTestFragmentAction));

        //RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TestsRecyclerAdapter newAdapter = new TestsRecyclerAdapter(getMockUpTests());
        recyclerView.setAdapter(newAdapter);


        return view;
    }

    private List<Test> getMockUpTests() {
        List<Test> tests = new ArrayList<Test>();
        tests.add(new Test(1, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(2, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(3, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(4, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(5, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(6, DateTime.now(), "UNKNOWN"));
        tests.add(new Test(7, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(8, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(9, DateTime.now(), "POSITIVE"));
        tests.add(new Test(10, DateTime.now(), "NEGATIVE"));
        tests.add(new Test(11, DateTime.now(), "NEGATIVE"));
        return tests;
    }
}
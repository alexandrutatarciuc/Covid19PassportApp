package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
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

        //Observer (creates new adapter, sets it for the recyclerView)
        final Observer<List<Test>> allTestsObserver = tests -> {
            TestsRecyclerAdapter newAdapter = new TestsRecyclerAdapter(tests);
            recyclerView.setAdapter(newAdapter);
        };

        //TestsViewModel
        testsViewModel = new ViewModelProvider(this).get(TestsViewModel.class);
        testsViewModel.getAllTests().observe(getViewLifecycleOwner(), allTestsObserver);

        //Floating action button
        actionButton = view.findViewById(R.id.testsFab);
        actionButton.setOnClickListener((v) -> NavHostFragment.findNavController(this).navigate(R.id.openAddTestFragmentAction));

        //RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
/*        TestsRecyclerAdapter newAdapter = new TestsRecyclerAdapter(getMockUpTests());
        recyclerView.setAdapter(newAdapter);*/



        return view;
    }

}
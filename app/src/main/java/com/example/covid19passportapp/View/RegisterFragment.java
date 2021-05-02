package com.example.covid19passportapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.covid19passportapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
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

        emailInput = view.findViewById(R.id.registerEmailEditInput);
        passwordInput = view.findViewById(R.id.registerPasswordEditInput);
        registerButton = view.findViewById(R.id.registerButton);
        backToLoginButton = view.findViewById(R.id.goBackToLoginButton);

        registerButton.setOnClickListener(v -> register());
        backToLoginButton.setOnClickListener(v -> backToLogin());

        return view;
    }

    private void register() {
        Log.d("REGISTER", "CLICKED");

        try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailInput.getText().toString(), passwordInput.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "User successfully registered", Toast.LENGTH_SHORT).show();
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
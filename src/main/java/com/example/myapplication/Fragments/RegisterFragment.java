package com.example.myapplication.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class RegisterFragment extends Fragment {

    private EditText usernameEditText;
    private EditText emailEditText;
    private Button registerButton;
    private EditText passwordEditText;
    private Button backButton;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        usernameEditText = view.findViewById(R.id.et_username);
        emailEditText = view.findViewById(R.id.et_email);
        registerButton = view.findViewById(R.id.btn_register_submit);
        passwordEditText = view.findViewById(R.id.et_password);
        backButton = view.findViewById(R.id.btn_back_to_start);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Please enter username, email, and password", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.apply();

                    ((MainActivity) requireActivity()).navigateToOverview(username);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).switchToFragment(new StartFragment());
            }
        });

        return view;
    }
}

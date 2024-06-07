package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.UserManager;

public class AccountSettingsFragment extends Fragment {

    private TextView profileNameTextView;
    private TextView profileEmailTextView;

    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);

        profileNameTextView = view.findViewById(R.id.profile_name);
        profileEmailTextView = view.findViewById(R.id.profile_email);
        Button deleteAccountButton = view.findViewById(R.id.delete_account_button);
        Button logoutButton = view.findViewById(R.id.logout_button);

        // Load user data
        loadUserData();

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void loadUserData() {
        UserManager userManager = ((MainActivity) getActivity()).getUserManager();
        String username = userManager.getUsername();
        String email = userManager.getEmail();

        profileNameTextView.setText(username);
        profileEmailTextView.setText(email);
    }

    private void deleteAccount() {
        UserManager userManager = ((MainActivity) getActivity()).getUserManager();
        userManager.clearUserCredentials();
        Toast.makeText(requireContext(), "Account successfully deleted", Toast.LENGTH_SHORT).show();
        navigateToStartFragment();
    }

    private void logout() {
        UserManager userManager = ((MainActivity) getActivity()).getUserManager();
        userManager.clearUserCredentials();
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
        navigateToStartFragment();
    }

    private void navigateToStartFragment() {
        ((MainActivity) requireActivity()).switchToFragment(new StartFragment());
    }
}

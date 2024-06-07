package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Fragments.OverviewFragment;
import com.example.myapplication.Fragments.StartFragment;

public class MainActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userManager = new UserManager(this);

        // Testweise Benutzer anmelden (hier sollten die Daten nach einem Login gespeichert werden)
        String username = "Alex";
        String email = "beispiel@email.com";
        userManager.saveUserLogin(username, email);

        // Fragment initialisieren
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new StartFragment())
                .commit();
    }

    public void switchToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void navigateToOverview(String username) {
        OverviewFragment overviewFragment = OverviewFragment.newInstance(username);
        switchToFragment(overviewFragment);
    }

    public UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager(this);
        }
        return userManager;
    }
}

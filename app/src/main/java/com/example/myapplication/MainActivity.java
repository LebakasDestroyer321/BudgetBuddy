package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String NASA_APOD_URL = "https://api.nasa.gov/planetary/apod?api_key=2ibnagYbSniGZCFUfHbfGjzpXsg1isD7N3kky52G";

    private DrawerLayout drawerLayout;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switchLayout("start");
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        drawerLayout = findViewById(R.id.drawer_layout);
        GetPictureOfTheDayData getPictureOfTheDayData = new GetPictureOfTheDayData();
        getPictureOfTheDayData.setMainViewModel(mainViewModel);
        getPictureOfTheDayData.execute(NASA_APOD_URL);
        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_dark_mode) {
                // Hier den Dark Mode ein-/ausschalten
                int nightModeFlags = getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
                if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                    // Dark Mode ist aktiviert, schalte ihn aus
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    // Dark Mode ist deaktiviert, schalte ihn ein
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

            }
            recreate();
            drawerLayout.closeDrawer(GravityCompat.END);
            return true;
        });
    }

    public void switchLayout(String layoutName) {
        if ("start".equals(layoutName)) {
            setContentView(R.layout.start);
        } else if ("hauptseite".equals(layoutName)) {
            setContentView(R.layout.hauptseite);
        } else if ("pictureoftheday".equals(layoutName)) {
            setContentView(R.layout.fragment_picture_of_the_day);
        } else if ("exoplanettracker".equals(layoutName)) {
            setContentView(R.layout.exoplanettracker);
        }
    }

    public void goToHauptseite(View view) {
        setContentView(R.layout.hauptseite);
    }
    public void goToPictureOfTheDay(View view) {
        setContentView(R.layout.fragment_picture_of_the_day);
    }
    public void goToExoplanetTracker(View view) {
        setContentView(R.layout.exoplanettracker);
    }

    public void goToZurueck(View view){
        setContentView(R.layout.hauptseite);
    }

    public void goToStart(View view){
        setContentView(R.layout.start);
    }
}
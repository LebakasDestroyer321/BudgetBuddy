package com.example.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.R;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch notificationsSwitch = view.findViewById(R.id.switch_notifications);
        Spinner currencySpinner = view.findViewById(R.id.spinner_currency);
        Button saveButton = view.findViewById(R.id.button_save_settings);

        // Configure spinner with currency options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.currency_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);

        // Lade gespeicherte Einstellungen
        loadSettings(notificationsSwitch, currencySpinner);

        // Setze den OnClickListener für den Speichern-Button
        saveButton.setOnClickListener(v -> {
            // Speichere die aktuellen Einstellungen
            saveSettings(notificationsSwitch, currencySpinner);

            // Verlasse dieses Fragment und kehre zum vorherigen zurück
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void saveSettings(Switch notificationsSwitch, Spinner currencySpinner) {
        SharedPreferences prefs = getActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Speichere den Status der Benachrichtigungen
        editor.putBoolean("NotificationsEnabled", notificationsSwitch.isChecked());

        // Speichere die gewählte Währung
        editor.putInt("SelectedCurrency", currencySpinner.getSelectedItemPosition());

        editor.apply(); // Änderungen speichern
        Toast.makeText(getContext(), "Settings saved", Toast.LENGTH_SHORT).show();
    }

    private void loadSettings(Switch notificationsSwitch, Spinner currencySpinner) {
        SharedPreferences prefs = getActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE);

        // Lade den Status der Benachrichtigungen
        boolean notificationsEnabled = prefs.getBoolean("NotificationsEnabled", false);
        notificationsSwitch.setChecked(notificationsEnabled);

        // Lade die gewählte Währung
        int selectedCurrencyPosition = prefs.getInt("SelectedCurrency", 0);
        currencySpinner.setSelection(selectedCurrencyPosition);
    }
}

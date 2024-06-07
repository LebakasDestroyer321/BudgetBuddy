package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String SHARED_PREF_NAME = "user_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    private SharedPreferences sharedPreferences;

    public UserManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserLogin(String username, String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void saveUserRegistration(String username, String email) {
        saveUserLogin(username, email);
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.contains(KEY_USERNAME) && sharedPreferences.contains(KEY_EMAIL);
    }

    public void clearUserCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_EMAIL);
        editor.apply();
    }
}

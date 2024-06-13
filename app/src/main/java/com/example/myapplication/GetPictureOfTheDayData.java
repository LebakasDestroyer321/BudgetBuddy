package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetPictureOfTheDayData extends AsyncTask<String, Void, String>{
    MainViewModel mainViewModel;
    public void setMainViewModel(MainViewModel mainViewModel){
        this.mainViewModel = mainViewModel;
    }
    @Override
    protected String doInBackground(String... urls) {
        if (urls.length == 0 || urls[0] == null) {
            return null; // Frühe Rückkehr, wenn keine URL übergeben wurde
        }

        String response = null;
        HttpURLConnection urlConnection = null;
        Scanner scanner = null;
        try {
            URL url = new URL(urls[0]); // Erste URL verwenden
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                response = scanner.next();
            }
        } catch (Exception e) {
            Log.e("GetPictureOfTheDayData", "Error during HTTP request", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (scanner != null) {
                scanner.close();
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Hier kannst du das Ergebnis verarbeiten, z.B. in ein ViewModel setzen oder direkt anzeigen
    }
}
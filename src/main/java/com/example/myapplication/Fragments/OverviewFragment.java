package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.API.ApiInterface;
import com.example.myapplication.API.RetrofitClientInstance;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Transaction;
import com.example.myapplication.TransactionAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverviewFragment extends Fragment {

    private TextView titleTextView;
    private TextView usernameTextView;
    private ImageView burgerButton;
    private List<Transaction> transactions;
    private Spinner filterSpinner;

    public static OverviewFragment newInstance(String username) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        titleTextView = view.findViewById(R.id.title_text_view);
        usernameTextView = view.findViewById(R.id.username_text_view);
        burgerButton = view.findViewById(R.id.burger_button);
        filterSpinner = view.findViewById(R.id.filter_spinner);

        String[] filterOptions = {"Alle", "Letzte 7 Tage", "Letzte 30 Tage", "Wohnkosten", "Einkommen", "Alltag", "Freizeit", "Transport", "Gesundheit", "Sonstiges"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, filterOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        titleTextView.setText("Übersicht");
        String username = getArguments().getString("username", "Default User");
        usernameTextView.setText("Logged in as: " + username);

        burgerButton.setOnClickListener(this::showPopupMenu);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedFilter = filterOptions[position];
                if (selectedFilter.equals("Alle")) {
                    fetchTransactionsData();
                } else if (selectedFilter.equals("Letzte 7 Tage")) {
                    fetchRecentTransactions(7);
                } else if (selectedFilter.equals("Letzte 30 Tage")) {
                    fetchRecentTransactions(30);
                } else if (selectedFilter.equals(("Wohnkosten")) || selectedFilter.equals("Einkommen") || selectedFilter.equals("Alltag") || selectedFilter.equals("Freizeit") || selectedFilter.equals("Transport") || selectedFilter.equals("Gesundheit") || selectedFilter.equals("Sonstiges")) {
                    fetchTransactionsByCategory(selectedFilter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        fetchTransactionsData();

        return view;
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_account) {
                ((MainActivity) requireActivity()).switchToFragment(new AccountSettingsFragment());
                return true;
            } else if (item.getItemId() == R.id.action_settings) {
                ((MainActivity) requireActivity()).switchToFragment(new SettingsFragment());
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void fetchTransactionsData() {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Transaction>> call = service.getAllTransactions();
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactions = response.body();
                    Log.d("API Success", "Transactions received: " + transactions.size());
                    for (Transaction transaction : transactions) {
                        Log.d("API Transaction", transaction.toString());
                    }
                    updateUI();
                } else {
                    Toast.makeText(getActivity(), "No transactions available or error in data retrieval", Toast.LENGTH_LONG).show();
                    Log.e("API Error", "Response Code: " + response.code());
                    Log.e("API Error", "Response Message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API Error", "Error Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Failure", t.getMessage(), t);
            }
        });
    }

    private void fetchRecentTransactions(int days) {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Transaction>> call = service.getRecentTransactions(days);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactions = response.body();
                    Log.d("API Success", "Transactions received: " + transactions.size());
                    for (Transaction transaction : transactions) {
                        Log.d("API Transaction", transaction.toString());
                    }
                    updateUI();
                } else {
                    Toast.makeText(getActivity(), "No transactions available or error in data retrieval", Toast.LENGTH_LONG).show();
                    Log.e("API Error", "Response Code: " + response.code());
                    Log.e("API Error", "Response Message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API Error", "Error Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Failure", t.getMessage(), t);
            }
        });
    }

    private void fetchTransactionsByCategory(String category) {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<List<Transaction>> call = service.getTransactionsByCategory(category);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactions = response.body();
                    Log.d("API Success", "Transactions received: " + transactions.size());
                    for (Transaction transaction : transactions) {
                        Log.d("API Transaction", transaction.toString());
                    }
                    updateUI();
                } else {
                    Toast.makeText(getActivity(), "No transactions available or error in data retrieval", Toast.LENGTH_LONG).show();
                    Log.e("API Error", "Response Code: " + response.code());
                    Log.e("API Error", "Response Message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API Error", "Error Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Failure", t.getMessage(), t);
            }
        });
    }

    private void updateTransaction(Long transactionId, Transaction updatedTransaction) {
        ApiInterface service = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<Transaction> call = service.updateTransaction(transactionId, updatedTransaction);
        call.enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Transaction transaction = response.body();
                    Log.d("API Success", "Transaction updated: " + transaction.toString());
                    fetchTransactionsData(); // Aktualisiere die UI mit den neuesten Daten
                } else {
                    Toast.makeText(getActivity(), "Error in updating transaction", Toast.LENGTH_LONG).show();
                    Log.e("API Error", "Response Code: " + response.code());
                    Log.e("API Error", "Response Message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("API Error", "Error Body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Failure", t.getMessage(), t);
            }
        });
    }

    private void updateUI() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_transactions);
        TransactionAdapter adapter = new TransactionAdapter(transactions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void showRetryButton() {
        Button retryButton = getView().findViewById(R.id.button_retry);
        retryButton.setVisibility(View.VISIBLE);
        retryButton.setOnClickListener(v -> {
            retryButton.setVisibility(View.GONE);
            fetchTransactionsData();
        });
    }
}

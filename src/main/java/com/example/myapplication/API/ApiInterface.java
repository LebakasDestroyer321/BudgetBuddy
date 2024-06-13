package com.example.myapplication.API;

import com.example.myapplication.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Body;

public interface ApiInterface {
    @GET("api/transactions")
    Call<List<Transaction>> getAllTransactions();

    @GET("api/transactions/recent")
    Call<List<Transaction>> getRecentTransactions(@Query("days") int days);

    @GET("api/transactions/category")
    Call<List<Transaction>> getTransactionsByCategory(@Query("category") String category);

    @PUT("api/transactions/{id}")
    Call<Transaction> updateTransaction(@Path("id") Long id, @Body Transaction transaction);
}

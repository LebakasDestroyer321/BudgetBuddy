package com.example.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(16, 16, 16, 16);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView nameTextView = new TextView(parent.getContext());
        nameTextView.setId(View.generateViewId());
        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        nameTextView.setTextSize(18); // Adjust text size
        layout.addView(nameTextView);

        TextView amountTextView = new TextView(parent.getContext());
        amountTextView.setId(View.generateViewId());
        amountTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        amountTextView.setTextSize(18); // Adjust text size
        layout.addView(amountTextView);

        return new TransactionViewHolder(layout, nameTextView, amountTextView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.transactionName.setText(transaction.getName());
        holder.transactionAmount.setText(String.format("$%.2f", transaction.getAmount()));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView transactionName;
        TextView transactionAmount;

        public TransactionViewHolder(@NonNull View itemView, TextView transactionName, TextView transactionAmount) {
            super(itemView);
            this.transactionName = transactionName;
            this.transactionAmount = transactionAmount;
        }
    }
}

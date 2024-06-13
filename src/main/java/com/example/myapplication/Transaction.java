package com.example.myapplication;

import java.math.BigDecimal;

public class Transaction {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String description;
    private String transactionDate;  // Als String behandeln

    public Transaction() {
    }

    public Transaction(Long id, String name, BigDecimal amount, String description, String transactionDate) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                '}';
    }
}
